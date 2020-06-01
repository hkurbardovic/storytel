package app.storytel.haris.com.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import app.storytel.haris.com.database.models.Photo
import app.storytel.haris.com.database.models.Post

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: Photo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<Post>)
}