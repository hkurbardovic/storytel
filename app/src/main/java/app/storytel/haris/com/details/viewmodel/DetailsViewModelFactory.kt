package app.storytel.haris.com.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.storytel.haris.com.details.repository.DetailsRepositoryImpl
import app.storytel.haris.com.di.scopes.ActivityScoped
import javax.inject.Inject

/**
 * Factory for creating a [DetailsViewModel] with a constructor that takes a [DetailsRepositoryImpl].
 */
@ActivityScoped
class DetailsViewModelFactory @Inject constructor(
    private val repository: DetailsRepositoryImpl
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        DetailsViewModel(repository) as T
}