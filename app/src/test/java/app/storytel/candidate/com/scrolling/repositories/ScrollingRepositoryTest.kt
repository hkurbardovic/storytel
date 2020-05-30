package app.storytel.candidate.com.scrolling.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.storytel.candidate.com.base.BaseRepository
import app.storytel.candidate.com.network.models.PhotoSchema
import app.storytel.candidate.com.network.models.PostSchema
import app.storytel.candidate.com.utilities.Event
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ScrollingRepositoryTest {

    private lateinit var scrollingRepositoryTestDouble: ScrollingRepositoryTestDouble

    @Before
    @Throws(Exception::class)
    fun setup() {
        scrollingRepositoryTestDouble = ScrollingRepositoryTestDouble()
    }

    @Test
    fun getPosts_exceptionThrown_loadingFalse() {
        scrollingRepositoryTestDouble.isExceptionThrown = true
        GlobalScope.launch {
            scrollingRepositoryTestDouble.getPosts()

            Assert.assertThat(
                scrollingRepositoryTestDouble.isLoadingLiveData.value,
                CoreMatchers.`is`(false)
            )
        }
    }

    @Test
    fun getPosts_exceptionThrown_errorMessageException() {
        scrollingRepositoryTestDouble.isExceptionThrown = true
        GlobalScope.launch {
            scrollingRepositoryTestDouble.getPosts()

            Assert.assertThat(
                scrollingRepositoryTestDouble.errorMessageLiveData.value?.getContentIfNotHandled(),
                CoreMatchers.`is`("exception")
            )
        }
    }

    @Test
    fun getPosts_exceptionThrown_postsNull() {
        scrollingRepositoryTestDouble.isExceptionThrown = true
        GlobalScope.launch {
            scrollingRepositoryTestDouble.getPosts()

            Assert.assertThat(
                scrollingRepositoryTestDouble.postsLiveData.value,
                CoreMatchers.`is`(listOf())
            )
        }
    }

    @Test
    fun getPosts_responseSuccessful_loadingFalse() {
        scrollingRepositoryTestDouble.isResponseSuccessful = true
        GlobalScope.launch {
            scrollingRepositoryTestDouble.getPosts()

            Assert.assertThat(
                scrollingRepositoryTestDouble.isLoadingLiveData.value,
                CoreMatchers.`is`(false)
            )
        }
    }

    @Test
    fun getPosts_responseSuccessful_postsData() {
        scrollingRepositoryTestDouble.isResponseSuccessful = true
        GlobalScope.launch {
            scrollingRepositoryTestDouble.getPosts()

            Assert.assertThat(
                scrollingRepositoryTestDouble.postsLiveData.value?.size,
                CoreMatchers.`is`(3)
            )
        }
    }

    @Test
    fun getPosts_responseUnsuccessful_errorMessageResponseFailed() {
        scrollingRepositoryTestDouble.isResponseSuccessful = true
        GlobalScope.launch {
            scrollingRepositoryTestDouble.getPosts()

            Assert.assertThat(
                scrollingRepositoryTestDouble.errorMessageLiveData.value?.getContentIfNotHandled(),
                CoreMatchers.`is`("response failed")
            )
        }
    }

    @Test
    fun getPhotos_exceptionThrown_loadingFalse() {
        scrollingRepositoryTestDouble.isExceptionThrown = true
        GlobalScope.launch {
            scrollingRepositoryTestDouble.getPhotos()

            Assert.assertThat(
                scrollingRepositoryTestDouble.isLoadingLiveData.value,
                CoreMatchers.`is`(false)
            )
        }
    }

    @Test
    fun getPhotos_exceptionThrown_errorMessageException() {
        scrollingRepositoryTestDouble.isExceptionThrown = true
        GlobalScope.launch {
            scrollingRepositoryTestDouble.getPhotos()

            Assert.assertThat(
                scrollingRepositoryTestDouble.errorMessageLiveData.value?.getContentIfNotHandled(),
                CoreMatchers.`is`("exception")
            )
        }
    }

    @Test
    fun getPhotos_exceptionThrown_postsNull() {
        scrollingRepositoryTestDouble.isExceptionThrown = true
        GlobalScope.launch {
            scrollingRepositoryTestDouble.getPhotos()

            Assert.assertThat(
                scrollingRepositoryTestDouble.photosLiveData.value,
                CoreMatchers.`is`(listOf())
            )
        }
    }

    @Test
    fun getPhotos_responseSuccessful_loadingFalse() {
        scrollingRepositoryTestDouble.isResponseSuccessful = true
        GlobalScope.launch {
            scrollingRepositoryTestDouble.getPhotos()

            Assert.assertThat(
                scrollingRepositoryTestDouble.isLoadingLiveData.value,
                CoreMatchers.`is`(false)
            )
        }
    }

    @Test
    fun getPhotos_responseSuccessful_postsData() {
        scrollingRepositoryTestDouble.isResponseSuccessful = true
        GlobalScope.launch {
            scrollingRepositoryTestDouble.getPhotos()

            Assert.assertThat(
                scrollingRepositoryTestDouble.photosLiveData.value?.size,
                CoreMatchers.`is`(3)
            )
        }
    }

    @Test
    fun getPhotos_responseUnsuccessful_errorMessageResponseFailed() {
        scrollingRepositoryTestDouble.isResponseSuccessful = true
        GlobalScope.launch {
            scrollingRepositoryTestDouble.getPhotos()

            Assert.assertThat(
                scrollingRepositoryTestDouble.errorMessageLiveData.value?.getContentIfNotHandled(),
                CoreMatchers.`is`("response failed")
            )
        }
    }

    private class ScrollingRepositoryTestDouble : BaseRepository(), ScrollingRepository {

        private val postsMutableLiveData = MutableLiveData<List<PostSchema?>?>()

        private val photosMutableLiveData = MutableLiveData<List<PhotoSchema?>?>()

        private val errorMessageMutableLiveData = MutableLiveData<Event<String?>?>()

        private val isLoadingMutableLiveData = MutableLiveData<Boolean>()

        val postsLiveData: LiveData<List<PostSchema?>?> = postsMutableLiveData

        val photosLiveData: LiveData<List<PhotoSchema?>?> = photosMutableLiveData

        val errorMessageLiveData: LiveData<Event<String?>?> = errorMessageMutableLiveData

        val isLoadingLiveData: LiveData<Boolean> = isLoadingMutableLiveData

        var isExceptionThrown: Boolean? = null
        var isResponseSuccessful: Boolean? = null

        override suspend fun getPosts() {
            isLoadingMutableLiveData.postValue(true)
            try {
                if (isExceptionThrown == true) throw java.lang.Exception()

                if (isResponseSuccessful == true) {
                    postsMutableLiveData.postValue(generatePosts())
                } else {
                    handleException(
                        errorMessageMutableLiveData,
                        "response failed",
                        "general error message"
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                handleException(
                    errorMessageMutableLiveData,
                    "exception",
                    "general error message"
                )
            } finally {
                isLoadingMutableLiveData.postValue(false)
            }
        }

        override suspend fun getPhotos() {
            isLoadingMutableLiveData.postValue(true)
            try {
                if (isExceptionThrown == true) throw java.lang.Exception()

                if (isResponseSuccessful == true) {
                    photosMutableLiveData.postValue(generatePhotos())
                } else {
                    handleException(
                        errorMessageMutableLiveData,
                        "response failed",
                        "general error message"
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                handleException(
                    errorMessageMutableLiveData,
                    "exception",
                    "general error message"
                )
            } finally {
                isLoadingMutableLiveData.postValue(false)
            }
        }

        private fun generatePosts(): List<PostSchema?>? {
            val postSchema1 = PostSchema(1, 1, "title1", "body1")
            val postSchema2 = PostSchema(2, 2, "title2", "body2")
            val postSchema3 = PostSchema(3, 3, "title3", "body3")

            return listOf(postSchema1, postSchema2, postSchema3)
        }

        private fun generatePhotos(): List<PhotoSchema?>? {
            val photoSchema1 = PhotoSchema(1, 1, "title1", "url1", "thumbnailUrl1")
            val photoSchema2 = PhotoSchema(2, 2, "title2", "url2", "thumbnailUrl2")
            val photoSchema3 = PhotoSchema(3, 3, "title1", "url3", "thumbnailUrl3")

            return listOf(photoSchema1, photoSchema2, photoSchema3)
        }
    }
}