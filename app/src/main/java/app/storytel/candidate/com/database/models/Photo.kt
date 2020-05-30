package app.storytel.candidate.com.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
class Photo(
    @PrimaryKey val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) {
    override fun toString() = title
}