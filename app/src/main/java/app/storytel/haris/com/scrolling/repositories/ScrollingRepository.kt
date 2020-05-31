package app.storytel.haris.com.scrolling.repositories

interface ScrollingRepository {

    suspend fun getPosts()

    suspend fun getPhotos()
}