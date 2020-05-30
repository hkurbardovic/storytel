package app.storytel.candidate.com.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.storytel.candidate.com.details.repository.DetailsRepositoryImpl
import javax.inject.Inject

/**
 * Factory for creating a [DetailsViewModel] with a constructor that takes a [DetailsRepositoryImpl].
 */
class DetailsViewModelFactory @Inject constructor(
    private val repository: DetailsRepositoryImpl
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        DetailsViewModel(repository) as T
}