package app.storytel.haris.com.network

import app.storytel.haris.com.network.models.CommentSchema
import app.storytel.haris.com.network.models.PhotoSchema
import app.storytel.haris.com.network.models.PostSchema
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): Response<List<PostSchema?>?>

    @GET("photos")
    suspend fun getPhotos(): Response<List<PhotoSchema?>?>

    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") id: Int): Response<List<CommentSchema?>?>
}