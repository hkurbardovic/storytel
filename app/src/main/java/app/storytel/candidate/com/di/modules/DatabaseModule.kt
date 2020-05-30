package app.storytel.candidate.com.di.modules

import app.storytel.candidate.com.app.StorytelApp
import app.storytel.candidate.com.database.StorytelDatabase
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