package app.storytel.candidate.com.scrolling

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import app.storytel.candidate.com.R
import app.storytel.candidate.com.adapters.PostAdapter
import app.storytel.candidate.com.base.BaseActivity
import app.storytel.candidate.com.commands.AddFragmentCommand
import app.storytel.candidate.com.databinding.ActivityScrollingBinding
import app.storytel.candidate.com.dialogs.DialogManager
import app.storytel.candidate.com.dialogs.TimeoutDialog
import app.storytel.candidate.com.fragments.TimeoutFragment
import app.storytel.candidate.com.scrolling.viewmodels.ScrollingViewModel
import app.storytel.candidate.com.scrolling.viewmodels.ScrollingViewModelFactory
import javax.inject.Inject

class ScrollingActivity : BaseActivity(), TimeoutDialog.ClickListener {

    @Inject
    lateinit var scrollingViewModelFactory: ScrollingViewModelFactory

    private val scrollingViewModel: ScrollingViewModel by viewModels {
        scrollingViewModelFactory
    }

    private lateinit var dialogManager: DialogManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityScrollingBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_scrolling)
        binding.lifecycleOwner = this
        binding.viewModel = scrollingViewModel

        val adapter = PostAdapter(this)
        binding.contentScrolling.recyclerView.adapter = adapter

        dialogManager = DialogManager()

        observeLiveData(binding, adapter)
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    override fun onRetryClicked() {
        scrollingViewModel.getPostsAndPhotos()
    }

    private fun observeLiveData(binding: ActivityScrollingBinding, adapter: PostAdapter) {
        scrollingViewModel.postsLiveData.observe(this) {
            if (it == null) return@observe
            val photos = scrollingViewModel.photosLiveData.value
            if (photos != null && photos.isNotEmpty()) {
                adapter.setPhotos(photos)
                adapter.submitList(it)
            }
        }
        scrollingViewModel.photosLiveData.observe(this) {
            if (it == null) return@observe
            val posts = scrollingViewModel.postsLiveData.value
            if (posts != null && posts.isNotEmpty()) {
                adapter.setPhotos(it)
                adapter.submitList(posts)
            }
        }
        scrollingViewModel.errorMessageLiveData.observe(this) {
            if (it == null || it.hasBeenHandled) return@observe
            showMessage(it.getContentIfNotHandled(), binding.coordinator)
        }
        scrollingViewModel.isTimeoutLiveData.observe(this) {
            if (it == null || it.hasBeenHandled) return@observe
            AddFragmentCommand(this, TimeoutFragment.newInstance()).execute()
        }
    }

    fun loadData() {
        scrollingViewModel.getPostsAndPhotos()
    }
}