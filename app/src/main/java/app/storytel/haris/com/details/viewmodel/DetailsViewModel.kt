package app.storytel.haris.com.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.storytel.haris.com.details.models.DetailsData
import app.storytel.haris.com.details.repository.DetailsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val repository: DetailsRepositoryImpl) :
    ViewModel() {

    private val detailsDataMutableLiveData = MutableLiveData<DetailsData?>()

    private val isFabVisibleMutableLiveData = MutableLiveData(true)

    val firstCommentLiveData = repository.firstCommentLiveData

    val secondCommentLiveData = repository.secondCommentLiveData

    val thirdCommentLiveData = repository.thirdCommentLiveData

    val errorMessageLiveData = repository.errorMessageLiveData

    val isLoadingLiveData = repository.isLoadingLiveData

    val isTimeoutLiveData = repository.isTimeoutLiveData

    val detailsDataLiveData: LiveData<DetailsData?> = detailsDataMutableLiveData

    val isFabVisibleLiveData: LiveData<Boolean> = isFabVisibleMutableLiveData

    fun getComments(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getComments(id)
        }
    }

    fun postDetailsData(value: DetailsData) {
        detailsDataMutableLiveData.postValue(value)
    }

    fun postIsFabVisible(value: Boolean) {
        isFabVisibleMutableLiveData.value = value
    }

    fun resetIsTimeout() {
        repository.resetIsTimeout()
    }
}