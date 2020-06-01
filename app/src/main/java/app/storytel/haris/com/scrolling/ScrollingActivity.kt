package app.storytel.haris.com.scrolling

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import app.storytel.haris.com.R
import app.storytel.haris.com.adapters.PostDbAdapter
import app.storytel.haris.com.base.BaseActivity
import app.storytel.haris.com.commands.AddFragmentCommand
import app.storytel.haris.com.commands.LaunchActivityCommand
import app.storytel.haris.com.databinding.ActivityScrollingBinding
import app.storytel.haris.com.dialogs.DialogManager
import app.storytel.haris.com.dialogs.TimeoutDialog
import app.storytel.haris.com.fragments.TimeoutFragment
import app.storytel.haris.com.network.handlers.NetworkConnectivityChangeHandler
import app.storytel.haris.com.network.handlers.NetworkConnectivityChangeListener
import app.storytel.haris.com.scrolling.viewmodels.ScrollingViewModel
import app.storytel.haris.com.scrolling.viewmodels.ScrollingViewModelFactory
import app.storytel.haris.com.settings.SettingsActivity
import app.storytel.haris.com.utilities.PreferencesUtils
import javax.inject.Inject

class ScrollingActivity : BaseActivity(), TimeoutDialog.ClickListener,
    NetworkConnectivityChangeListener {

    @Inject
    lateinit var networkConnectivityChangeHandler: NetworkConnectivityChangeHandler

    @Inject
    lateinit var dialogManager: DialogManager

    @Inject
    lateinit var scrollingViewModelFactory: ScrollingViewModelFactory

    private var isDialogShown = false

    private val scrollingViewModel: ScrollingViewModel by viewModels {
        scrollingViewModelFactory
    }

    private lateinit var binding: ActivityScrollingBinding

    override fun isDI() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scrolling)
        binding.lifecycleOwner = this
        binding.viewModel = scrollingViewModel

        setSupportActionBar(binding.toolbar)

        val adapter = PostDbAdapter(this)
        binding.contentScrolling.recyclerView.adapter = adapter

        observeLiveData(binding, adapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                LaunchActivityCommand(this, SettingsActivity::class.java).execute()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_DIALOG_SHOWN_KEY, isDialogShown)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isDialogShown = savedInstanceState.getBoolean(IS_DIALOG_SHOWN_KEY)
    }

    override fun onRetryClicked() {
        scrollingViewModel.getPostsAndPhotos()
    }

    override fun onDismiss() {
        isDialogShown = false
    }

    override fun onAvailable() {
        loadData()
    }

    override fun onLost() {
        showMessage(getString(R.string.no_internet_connection), binding.coordinator)
    }

    private fun observeLiveData(binding: ActivityScrollingBinding, adapter: PostDbAdapter) {
        scrollingViewModel.postsLiveData.observe(this) {
            adapter.submitList(it)
        }
        scrollingViewModel.photosLiveData.observe(this) {
            if (it == null) return@observe
            adapter.setPhotos(it)
        }
        scrollingViewModel.errorMessageLiveData.observe(this) {
            if (it == null || it.hasBeenHandled) return@observe
            showMessage(it.getContentIfNotHandled(), binding.coordinator)
        }
        scrollingViewModel.isTimeoutLiveData.observe(this) {
            if (it == null || it.hasBeenHandled || it.getContentIfNotHandled() == false) return@observe
            if (PreferencesUtils.isTimeoutDialog(this)) {
                if (!isDialogShown) {
                    dialogManager.showTimeoutDialog(supportFragmentManager)
                    isDialogShown = true
                }
            } else {
                AddFragmentCommand(this, TimeoutFragment.newInstance()).execute()
            }

            scrollingViewModel.resetIsTimeout()
        }
    }

    fun loadData() {
        scrollingViewModel.getPostsAndPhotos()
    }

    fun showFab() {
        scrollingViewModel.postIsFabVisible(true)
    }

    fun hideFab() {
        scrollingViewModel.postIsFabVisible(false)
    }

    companion object {
        private const val IS_DIALOG_SHOWN_KEY =
            "app.storytel.haris.com.scrolling.is_dialog_shown_key"
    }
}