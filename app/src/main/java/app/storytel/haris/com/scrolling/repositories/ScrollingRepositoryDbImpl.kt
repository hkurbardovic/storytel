package app.storytel.haris.com.scrolling.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import app.storytel.haris.com.R
import app.storytel.haris.com.base.BaseRepository
import app.storytel.haris.com.database.DatabaseHandler
import app.storytel.haris.com.database.StorytelDatabase
import app.storytel.haris.com.database.models.Post
import app.storytel.haris.com.network.ApiService
import app.storytel.haris.com.network.models.PhotoSchema
import app.storytel.haris.com.utilities.Event
import java.net.SocketTimeoutException
import javax.inject.Inject

class ScrollingRepositoryDbImpl @Inject constructor(
    private val context: Context,
    private val apiService: ApiService,
    database: StorytelDatabase,
    private val databaseHandler: DatabaseHandler
) : BaseRepository(), ScrollingRepository {

    private val photosMutableLiveData = MutableLiveData<List<PhotoSchema?>?>()

    private val errorMessageMutableLiveData = MutableLiveData<Event<String?>?>()

    private val isLoadingMutableLiveData = MutableLiveData<Boolean?>()

    private val isTimeoutMutableLiveData = MutableLiveData<Event<Boolean?>?>()

    val postsLiveData: LiveData<PagedList<Post>> =
        database.postDao().getAll().toLiveData(20)

    val photosLiveData: LiveData<List<PhotoSchema?>?> = photosMutableLiveData

    val errorMessageLiveData: LiveData<Event<String?>?> = errorMessageMutableLiveData

    val isLoadingLiveData: LiveData<Boolean?> = isLoadingMutableLiveData

    val isTimeoutLiveData: LiveData<Event<Boolean?>?> = isTimeoutMutableLiveData

    override suspend fun getPosts() {
        isLoadingMutableLiveData.postValue(true)
        try {
            val response = apiService.getPosts()

            if (response.isSuccessful) {
                databaseHandler.insertPostsDatabase(response.body())
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