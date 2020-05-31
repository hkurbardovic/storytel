package app.storytel.haris.com.di.modules

import app.storytel.haris.com.app.StorytelApp
import app.storytel.haris.com.database.StorytelDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun database(application: StorytelApp): StorytelDatabase {
        return StorytelDatabase.getInstance(application)
    }
}