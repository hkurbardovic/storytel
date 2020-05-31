package app.storytel.haris.com.base

import androidx.lifecycle.MutableLiveData
import app.storytel.haris.com.utilities.Event

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