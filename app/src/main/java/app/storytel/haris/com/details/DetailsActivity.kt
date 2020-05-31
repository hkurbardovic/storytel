package app.storytel.haris.com.details

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import app.storytel.haris.com.R
import app.storytel.haris.com.base.BaseActivity
import app.storytel.haris.com.commands.AddFragmentCommand
import app.storytel.haris.com.databinding.ActivityDetailsBinding
import app.storytel.haris.com.details.models.DetailsData
import app.storytel.haris.com.details.viewmodel.DetailsViewModel
import app.storytel.haris.com.details.viewmodel.DetailsViewModelFactory
import app.storytel.haris.com.dialogs.DialogManager
import app.storytel.haris.com.dialogs.TimeoutDialog
import app.storytel.haris.com.fragments.TimeoutFragment
import app.storytel.haris.com.network.handlers.NetworkConnectivityChangeHandler
import app.storytel.haris.com.network.handlers.NetworkConnectivityChangeListener
import app.storytel.haris.com.utilities.PreferencesUtils
import javax.inject.Inject

class DetailsActivity : BaseActivity(), NetworkConnectivityChangeListener,
    TimeoutDialog.ClickListener {

    @Inject
    lateinit var networkConnectivityChangeHandler: NetworkConnectivityChangeHandler

    @Inject
    lateinit var dialogManager: DialogManager

    @Inject
    lateinit var detailsViewModelFactory: DetailsViewModelFactory

    private val detailsViewModel: DetailsViewModel by viewModels {
        detailsViewModelFactory
    }

    private lateinit var binding: ActivityDetailsBinding

    private var id: Int? = null

    override fun isDI() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding.lifecycleOwner = this

        binding.viewModel = detailsViewModel

        binding.toolbar.title = ""
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras

        observeLiveData()

        id = bundle?.getInt(ID_KEY)
        setBundleData(bundle)
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkConnectivityChangeHandler.setCallback(this)
        }
        loadData()
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkConnectivityChangeHandler.removeCallback()
        }
    }

    override fun onAvailable() {
        loadData()
    }

    override fun onLost() {
        showMessage(getString(R.string.no_internet_connection), binding.coordinator)
    }

    override fun onRetryClicked() {
        loadData()
    }

    private fun observeLiveData() {
        detailsViewModel.detailsDataLiveData.observe(this) {
            if (it == null) return@observe

            supportActionBar?.title = it.title
        }
        detailsViewModel.errorMessageLiveData.observe(this) {
            if (it == null || it.hasBeenHandled) return@observe
            showMessage(it.getContentIfNotHandled(), binding.coordinator)
        }
        detailsViewModel.isTimeoutLiveData.observe(this) {
            if (it == null || it.hasBeenHandled) return@observe
            if (PreferencesUtils.isTimeoutDialog(this)) {
                dialogManager.showTimeoutDialog(supportFragmentManager)
            } else {
                AddFragmentCommand(this, TimeoutFragment.newInstance()).execute()
            }
        }
    }

    private fun setBundleData(bundle: Bundle?) {
        if (bundle == null) return

        detailsViewModel.postDetailsData(
            DetailsData(
                bundle.getString(TITLE_KEY) ?: "",
                bundle.getString(BODY_KEY) ?: "",
                bundle.getString(URL_KEY) ?: ""
            )
        )
    }

    fun loadData() {
        val id = id ?: return
        detailsViewModel.getComments(id)
    }

    fun showFab() {
        detailsViewModel.postIsFabVisible(true)
    }

    fun hideFab() {
        detailsViewModel.postIsFabVisible(false)
    }

    companion object {
        const val ID_KEY = "app.storytel.haris.com.details.id"
        const val TITLE_KEY = "app.storytel.haris.com.details.title"
        const val BODY_KEY = "app.storytel.haris.com.details.body"
        const val URL_KEY = "app.storytel.haris.com.details.url"
    }
}