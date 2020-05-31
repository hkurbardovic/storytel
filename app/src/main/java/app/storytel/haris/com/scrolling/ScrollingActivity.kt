package app.storytel.haris.com.scrolling

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import app.storytel.haris.com.R
import app.storytel.haris.com.adapters.PostAdapter
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

        val adapter = PostAdapter(this)
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

    override fun onRetryClicked() {
        scrollingViewModel.getPostsAndPhotos()
    }

    override fun onAvailable() {
        loadData()
    }

    override fun onLost() {
        showMessage(getString(R.string.no_internet_connection), binding.coordinator)
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
            if (PreferencesUtils.isTimeoutDialog(this)) {
                dialogManager.showTimeoutDialog(supportFragmentManager)
            } else {
                AddFragmentCommand(this, TimeoutFragment.newInstance()).execute()
            }
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
}