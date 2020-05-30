package app.storytel.candidate.com.scrolling.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.storytel.candidate.com.scrolling.repositories.ScrollingRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScrollingViewModel @Inject constructor(private val repository: ScrollingRepositoryImpl) :
    ViewModel() {

    val postsLiveData = repository.postsLiveData

    val photosLiveData = repository.photosLiveData

    val errorMessageLiveData = repository.errorMessageLiveData

    val isLoadingLiveData = repository.isLoadingLiveData

    val isTimeoutLiveData = repository.isTimeoutLiveData

    fun getPostsAndPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPhotos()
            repository.getPosts()
        }
    }
}