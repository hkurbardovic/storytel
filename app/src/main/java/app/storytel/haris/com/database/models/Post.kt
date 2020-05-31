package app.storytel.haris.com.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
class Post(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String
) {
    override fun toString() = title
}