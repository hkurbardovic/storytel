package app.storytel.haris.com.database.daos

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.storytel.haris.com.database.models.Post

@Dao
interface PostDao {

    @Query("SELECT * FROM post")
    fun getAll(): DataSource.Factory<Int, Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<Post>)
}