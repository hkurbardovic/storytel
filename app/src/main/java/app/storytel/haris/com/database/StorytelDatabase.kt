package app.storytel.haris.com.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.storytel.haris.com.database.daos.PhotoDao
import app.storytel.haris.com.database.daos.PostDao
import app.storytel.haris.com.database.models.Photo
import app.storytel.haris.com.database.models.Post

/**
 * The Room database for this app
 */
@Database(entities = [Post::class, Photo::class], version = 1, exportSchema = false)
abstract class StorytelDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao

    abstract fun photoDao(): PhotoDao

    companion object {

        private const val DB_NAME = "storytel"

        // For Singleton instantiation
        @Volatile
        private var instance: StorytelDatabase? = null

        fun getInstance(context: Context): StorytelDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): StorytelDatabase {
            return Room.databaseBuilder(context, StorytelDatabase::class.java, DB_NAME).build()
        }
    }
}