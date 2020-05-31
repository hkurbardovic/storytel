package app.storytel.haris.com.database.daos

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.storytel.haris.com.models.PostPhoto
import app.storytel.haris.com.database.models.Photo

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo join post")
    fun getAll(): DataSource.Factory<Int, PostPhoto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: Photo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(photos: List<Photo>)
}