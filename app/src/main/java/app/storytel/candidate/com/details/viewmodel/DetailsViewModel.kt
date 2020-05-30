package app.storytel.candidate.com.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.storytel.candidate.com.details.models.DetailsData
import app.storytel.candidate.com.details.repository.DetailsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val repository: DetailsRepositoryImpl) :
    ViewModel() {

    private val detailsDataMutableLiveData = MutableLiveData<DetailsData?>()

    val firstCommentLiveData = repository.firstCommentLiveData

    val secondCommentLiveData = repository.secondCommentLiveData

    val thirdCommentLiveData = repository.thirdCommentLiveData

    val errorMessageLiveData = repository.errorMessageLiveData

    val isLoadingLiveData = repository.isLoadingLiveData

    val isTimeoutLiveData = repository.isTimeoutLiveData

    val detailsDataLiveData: LiveData<DetailsData?> = detailsDataMutableLiveData


    fun getComments(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getComments(id)
        }
    }

    fun postDetailsData(value: DetailsData) {
        detailsDataMutableLiveData.postValue(value)
    }
}