package app.storytel.candidate.com.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import app.storytel.candidate.com.R
import app.storytel.candidate.com.base.BaseActivity
import app.storytel.candidate.com.commands.AddFragmentCommand
import app.storytel.candidate.com.databinding.ActivityDetailsBinding
import app.storytel.candidate.com.details.models.DetailsData
import app.storytel.candidate.com.details.viewmodel.DetailsViewModel
import app.storytel.candidate.com.details.viewmodel.DetailsViewModelFactory
import app.storytel.candidate.com.fragments.TimeoutFragment
import javax.inject.Inject

class DetailsActivity : BaseActivity() {

    @Inject
    lateinit var detailsViewModelFactory: DetailsViewModelFactory

    private val detailsViewModel: DetailsViewModel by viewModels {
        detailsViewModelFactory
    }

    private lateinit var binding: ActivityDetailsBinding

    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding.lifecycleOwner = this

        binding.viewModel = detailsViewModel

        val bundle = intent.extras

        observeLiveData()

        id = bundle?.getInt(ID_KEY)
        setBundleData(bundle)
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun observeLiveData() {
        detailsViewModel.errorMessageLiveData.observe(this) {
            if (it == null || it.hasBeenHandled) return@observe
            showMessage(it.getContentIfNotHandled(), binding.coordinator)
        }
        detailsViewModel.isTimeoutLiveData.observe(this) {
            if (it == null || it.hasBeenHandled) return@observe
            AddFragmentCommand(this, TimeoutFragment.newInstance()).execute()
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

    companion object {
        const val ID_KEY = "app.storytel.candidate.com.details.id"
        const val TITLE_KEY = "app.storytel.candidate.com.details.title"
        const val BODY_KEY = "app.storytel.candidate.com.details.body"
        const val URL_KEY = "app.storytel.candidate.com.details.url"
    }
}