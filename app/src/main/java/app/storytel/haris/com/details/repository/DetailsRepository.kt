package app.storytel.haris.com.details.repository

interface DetailsRepository {

    suspend fun getComments(id: Int)
}