package app.storytel.candidate.com.details.repository

interface DetailsRepository {

    suspend fun getComments(id: Int)
}