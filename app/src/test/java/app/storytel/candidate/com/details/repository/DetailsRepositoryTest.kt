package app.storytel.candidate.com.details.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.storytel.candidate.com.base.BaseRepository
import app.storytel.candidate.com.network.models.CommentSchema
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
class DetailsRepositoryTest {

    private val id = 1

    private lateinit var detailsRepositoryTestDouble: DetailsRepositoryTestDouble

    @Before
    @Throws(Exception::class)
    fun setup() {
        detailsRepositoryTestDouble = DetailsRepositoryTestDouble()
    }

    @Test
    fun getComments_exceptionThrown_loadingFalse() {
        detailsRepositoryTestDouble.isExceptionThrown = true
        GlobalScope.launch {
            detailsRepositoryTestDouble.getComments(id)

            Assert.assertThat(
                detailsRepositoryTestDouble.isLoadingLiveData.value,
                CoreMatchers.`is`(false)
            )
        }
    }

    @Test
    fun getComments_exceptionThrown_errorMessageException() {
        detailsRepositoryTestDouble.isExceptionThrown = true
        GlobalScope.launch {
            detailsRepositoryTestDouble.getComments(id)

            Assert.assertThat(
                detailsRepositoryTestDouble.errorMessageLiveData.value?.getContentIfNotHandled(),
                CoreMatchers.`is`("exception")
            )
        }
    }

    @Test
    fun getComments_responseSuccessful_loadingFalse() {
        detailsRepositoryTestDouble.isResponseSuccessful = true
        GlobalScope.launch {
            detailsRepositoryTestDouble.getComments(id)

            Assert.assertThat(
                detailsRepositoryTestDouble.isLoadingLiveData.value,
                CoreMatchers.`is`(false)
            )
        }
    }

    @Test
    fun getComments_responseSuccessful_commentsData() {
        detailsRepositoryTestDouble.isResponseSuccessful = true
        GlobalScope.launch {
            detailsRepositoryTestDouble.getComments(id)

            Assert.assertThat(
                detailsRepositoryTestDouble.firstCommentLiveData.value?.name,
                CoreMatchers.`is`("name1")
            )
            Assert.assertThat(
                detailsRepositoryTestDouble.secondCommentLiveData.value?.name,
                CoreMatchers.`is`("name2")
            )
            Assert.assertThat(
                detailsRepositoryTestDouble.thirdCommentLiveData.value?.name,
                CoreMatchers.`is`("name3")
            )
        }
    }

    @Test
    fun getComments_responseUnsuccessful_errorMessageResponseFailed() {
        detailsRepositoryTestDouble.isResponseSuccessful = true
        GlobalScope.launch {
            detailsRepositoryTestDouble.getComments(id)

            Assert.assertThat(
                detailsRepositoryTestDouble.errorMessageLiveData.value?.getContentIfNotHandled(),
                CoreMatchers.`is`("response failed")
            )
        }
    }

    private class DetailsRepositoryTestDouble : BaseRepository(), DetailsRepository {

        private val firstCommentMutableLiveData = MutableLiveData<CommentSchema?>()

        private val secondCommentMutableLiveData = MutableLiveData<CommentSchema?>()

        private val thirdCommentMutableLiveData = MutableLiveData<CommentSchema?>()

        private val errorMessageMutableLiveData = MutableLiveData<Event<String?>?>()

        private val isLoadingMutableLiveData = MutableLiveData<Boolean>()

        val firstCommentLiveData: LiveData<CommentSchema?> = firstCommentMutableLiveData

        val secondCommentLiveData: LiveData<CommentSchema?> = secondCommentMutableLiveData

        val thirdCommentLiveData: LiveData<CommentSchema?> = thirdCommentMutableLiveData

        val errorMessageLiveData: LiveData<Event<String?>?> = errorMessageMutableLiveData

        val isLoadingLiveData: LiveData<Boolean> = isLoadingMutableLiveData

        var isExceptionThrown: Boolean? = null
        var isResponseSuccessful: Boolean? = null

        override suspend fun getComments(id: Int) {
            isLoadingMutableLiveData.postValue(true)
            try {
                if (isExceptionThrown == true) throw java.lang.Exception()

                if (isResponseSuccessful == true) {
                    handleComments(generateComments())
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

        private fun generateComments(): List<CommentSchema?>? {
            val commentSchema1 = CommentSchema(1, 1, "name1", "email1", "body1")
            val commentSchema2 = CommentSchema(2, 2, "name2", "email2", "body2")
            val commentSchema3 = CommentSchema(3, 3, "name3", "email3", "body3")

            return listOf(commentSchema1, commentSchema2, commentSchema3)
        }

        private fun handleComments(data: List<CommentSchema?>?) {
            if (data == null) return
            if (data.isNotEmpty()) firstCommentMutableLiveData.postValue(data[0]) else return
            if (data.size > 1) secondCommentMutableLiveData.postValue(data[1]) else return
            if (data.size > 2) thirdCommentMutableLiveData.postValue(data[2]) else return
        }
    }
}