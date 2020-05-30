package app.storytel.candidate.com.details.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.storytel.candidate.com.R
import app.storytel.candidate.com.base.BaseRepository
import app.storytel.candidate.com.network.ApiService
import app.storytel.candidate.com.network.models.CommentSchema
import app.storytel.candidate.com.utilities.Event
import java.net.SocketTimeoutException
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val context: Context,
    private val apiService: ApiService
) : BaseRepository(), DetailsRepository {

    private val firstCommentMutableLiveData = MutableLiveData<CommentSchema?>()

    private val secondCommentMutableLiveData = MutableLiveData<CommentSchema?>()

    private val thirdCommentMutableLiveData = MutableLiveData<CommentSchema?>()

    private val errorMessageMutableLiveData = MutableLiveData<Event<String?>?>()

    private val isLoadingMutableLiveData = MutableLiveData<Boolean>()

    private val isTimeoutMutableLiveData = MutableLiveData<Event<Boolean?>?>()

    val firstCommentLiveData: LiveData<CommentSchema?> = firstCommentMutableLiveData

    val secondCommentLiveData: LiveData<CommentSchema?> = secondCommentMutableLiveData

    val thirdCommentLiveData: LiveData<CommentSchema?> = thirdCommentMutableLiveData

    val errorMessageLiveData: LiveData<Event<String?>?> = errorMessageMutableLiveData

    val isLoadingLiveData: LiveData<Boolean> = isLoadingMutableLiveData

    val isTimeoutLiveData: LiveData<Event<Boolean?>?> = isTimeoutMutableLiveData

    private fun handleComments(data: List<CommentSchema?>?) {
        if (data == null) return
        if (data.isNotEmpty()) firstCommentMutableLiveData.postValue(data[0]) else return
        if (data.size > 1) secondCommentMutableLiveData.postValue(data[1]) else return
        if (data.size > 2) thirdCommentMutableLiveData.postValue(data[2]) else return
    }

    override suspend fun getComments(id: Int) {
        isLoadingMutableLiveData.postValue(true)
        try {
            val response = apiService.getComments(id)

            if (response.isSuccessful) {
                handleComments(response.body())
            } else {
                handleException(
                    errorMessageMutableLiveData,
                    response.message(),
                    context.getString(R.string.general_error_message)
                )
            }
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
            isTimeoutMutableLiveData.postValue(Event(true))
        } catch (e: Exception) {
            e.printStackTrace()
            handleException(
                errorMessageMutableLiveData,
                e.localizedMessage ?: "",
                context.getString(R.string.general_error_message)
            )
        } finally {
            isLoadingMutableLiveData.postValue(false)
        }
    }
}