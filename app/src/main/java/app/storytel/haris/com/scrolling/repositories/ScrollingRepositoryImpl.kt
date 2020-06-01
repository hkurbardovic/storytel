package app.storytel.haris.com.scrolling.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.storytel.haris.com.R
import app.storytel.haris.com.base.BaseRepository
import app.storytel.haris.com.di.scopes.ActivityScoped
import app.storytel.haris.com.network.ApiService
import app.storytel.haris.com.network.models.PhotoSchema
import app.storytel.haris.com.network.models.PostSchema
import app.storytel.haris.com.utilities.Event
import kotlinx.coroutines.delay
import java.net.SocketTimeoutException
import javax.inject.Inject

@ActivityScoped
class ScrollingRepositoryImpl @Inject constructor(
    private val context: Context,
    private val apiService: ApiService
) : BaseRepository(), ScrollingRepository {

    private val postsMutableLiveData = MutableLiveData<List<PostSchema?>?>()

    private val photosMutableLiveData = MutableLiveData<List<PhotoSchema?>?>()

    private val errorMessageMutableLiveData = MutableLiveData<Event<String?>?>()

    private val isLoadingMutableLiveData = MutableLiveData<Boolean?>()

    private val isTimeoutMutableLiveData = MutableLiveData<Event<Boolean?>?>()

    val postsLiveData: LiveData<List<PostSchema?>?> = postsMutableLiveData

    val photosLiveData: LiveData<List<PhotoSchema?>?> = photosMutableLiveData

    val errorMessageLiveData: LiveData<Event<String?>?> = errorMessageMutableLiveData

    val isLoadingLiveData: LiveData<Boolean?> = isLoadingMutableLiveData

    val isTimeoutLiveData: LiveData<Event<Boolean?>?> = isTimeoutMutableLiveData

    override suspend fun getPosts() {
        isLoadingMutableLiveData.postValue(true)
        try {
            val response = apiService.getPosts()

            if (response.isSuccessful) {
                postsMutableLiveData.postValue(response.body())
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

    override suspend fun getPhotos() {
        isLoadingMutableLiveData.postValue(true)
        try {
            val response = apiService.getPhotos()

            if (response.isSuccessful) {
                photosMutableLiveData.postValue(response.body())
            } else {
                handleException(
                    errorMessageMutableLiveData,
                    response.message(),
                    context.getString(R.string.general_error_message)
                )
            }
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
            delay(2000)
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

    fun resetIsTimeout() {
        isTimeoutMutableLiveData.postValue(Event(false))
    }
}