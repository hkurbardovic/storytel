package app.storytel.candidate.com.database

import app.storytel.candidate.com.network.models.PostSchema
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
}