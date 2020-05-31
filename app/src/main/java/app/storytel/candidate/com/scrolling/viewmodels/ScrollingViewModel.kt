package app.storytel.candidate.com.scrolling.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.storytel.candidate.com.scrolling.repositories.ScrollingRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScrollingViewModel @Inject constructor(private val repository: ScrollingRepositoryImpl) :
    ViewModel() {

    private val isFabVisibleMutableLiveData = MutableLiveData(true)

    val postsLiveData = repository.postsLiveData

    val photosLiveData = repository.photosLiveData

    val errorMessageLiveData = repository.errorMessageLiveData

    val isLoadingLiveData = repository.isLoadingLiveData

    val isTimeoutLiveData = repository.isTimeoutLiveData

    val isFabVisibleLiveData: LiveData<Boolean> = isFabVisibleMutableLiveData

    fun getPostsAndPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPhotos()
            repository.getPosts()
        }
    }

    fun postIsFabVisible(value: Boolean) {
        isFabVisibleMutableLiveData.value = value
    }
}