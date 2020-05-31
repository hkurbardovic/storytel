package app.storytel.haris.com.database

import app.storytel.haris.com.network.models.PhotoSchema
import app.storytel.haris.com.network.models.PostSchema
import javax.inject.Inject

class DatabaseHandler @Inject constructor(private val database: StorytelDatabase) {

    fun insertPostsDatabase(posts: List<PostSchema?>?) {
        posts?.let { nonNullablePosts ->
            for (post in nonNullablePosts) {
                post?.toPost()?.let { nonNullablePost ->
                    database.postDao().insertPost(nonNullablePost)
                }
            }
        }
    }

    fun insertPhotosDatabase(photos: List<PhotoSchema?>?) {
        photos?.let { nonNullablePhotos ->
            for (photo in nonNullablePhotos) {
                photo?.toPhoto()?.let { nonNullablePhoto ->
                    database.postDao().insertPhoto(nonNullablePhoto)
                }
            }
        }
    }
}