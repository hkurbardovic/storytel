package app.storytel.candidate.com.scrolling.repositories

interface ScrollingRepository {

    suspend fun getPosts()

    suspend fun getPhotos()
}