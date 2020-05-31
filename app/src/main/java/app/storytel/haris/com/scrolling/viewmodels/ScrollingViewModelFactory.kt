package app.storytel.haris.com.scrolling.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.storytel.haris.com.di.scopes.ActivityScoped
import app.storytel.haris.com.scrolling.repositories.ScrollingRepositoryImpl
import javax.inject.Inject

/**
 * Factory for creating a [ScrollingViewModel] with a constructor that takes a [ScrollingRepositoryImpl].
 */
@ActivityScoped
class ScrollingViewModelFactory @Inject constructor(
    private val repository: ScrollingRepositoryImpl
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        ScrollingViewModel(repository) as T
}