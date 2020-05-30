package app.storytel.candidate.com.base

import androidx.lifecycle.MutableLiveData
import app.storytel.candidate.com.utilities.Event

abstract class BaseRepository {

    fun handleException(
        errorMessageMutableLiveData: MutableLiveData<Event<String?>?>,
        errorMessage: String,
        generalErrorMessage: String
    ) {
        errorMessageMutableLiveData.postValue(
            if (errorMessage.isNotBlank()) Event(errorMessage) else Event(
                generalErrorMessage
            )
        )
    }
}