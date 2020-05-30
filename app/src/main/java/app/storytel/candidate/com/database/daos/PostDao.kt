package app.storytel.candidate.com.database.daos

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.storytel.candidate.com.database.models.Photo
import app.storytel.candidate.com.database.models.Post
import app.storytel.candidate.com.models.PostPhoto

@Dao
interface PostDao {

    @Query("SELECT * FROM photo inner join post on photo.id LIKE post.id")
    fun getAll(): DataSource.Factory<Int, PostPhoto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: Photo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<Post>)
}