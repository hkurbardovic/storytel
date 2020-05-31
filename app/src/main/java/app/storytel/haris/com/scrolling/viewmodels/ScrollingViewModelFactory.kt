package app.storytel.haris.com.scrolling.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.storytel.haris.com.di.scopes.ActivityScoped
import app.storytel.haris.com.scrolling.repositories.ScrollingRepositoryDbImpl
import javax.inject.Inject

/**
 * Factory for creating a [ScrollingViewModel] with a constructor that takes a [ScrollingRepositoryDbImpl].
 */
@ActivityScoped
class ScrollingViewModelFactory @Inject constructor(
    private val repository: ScrollingRepositoryDbImpl
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        ScrollingViewModel(repository) as T
}