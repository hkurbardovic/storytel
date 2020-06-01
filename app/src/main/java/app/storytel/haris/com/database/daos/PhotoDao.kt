package app.storytel.haris.com.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import app.storytel.haris.com.database.models.Photo

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: Photo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(photos: List<Photo>)
}