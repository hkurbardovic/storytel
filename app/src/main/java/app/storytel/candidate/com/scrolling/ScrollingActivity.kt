package app.storytel.candidate.com.scrolling

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import app.storytel.candidate.com.R
import app.storytel.candidate.com.adapters.PostDbAdapter
import app.storytel.candidate.com.base.BaseActivity
import app.storytel.candidate.com.commands.AddFragmentCommand
import app.storytel.candidate.com.commands.LaunchActivityCommand
import app.storytel.candidate.com.databinding.ActivityScrollingBinding
import app.storytel.candidate.com.dialogs.DialogManager
import app.storytel.candidate.com.dialogs.TimeoutDialog
import app.storytel.candidate.com.fragments.TimeoutFragment
import app.storytel.candidate.com.network.handlers.NetworkConnectivityChangeHandler
import app.storytel.candidate.com.network.handlers.NetworkConnectivityChangeListener
import app.storytel.candidate.com.scrolling.viewmodels.ScrollingViewModel
import app.storytel.candidate.com.scrolling.viewmodels.ScrollingViewModelFactory
import app.storytel.candidate.com.settings.SettingsActivity
import app.storytel.candidate.com.utilities.PreferencesUtils
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

    override fun onRetryClicked() {
        scrollingViewModel.getPostsAndPhotos()
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