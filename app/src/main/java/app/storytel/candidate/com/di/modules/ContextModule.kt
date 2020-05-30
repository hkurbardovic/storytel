package app.storytel.candidate.com.di.modules

import android.content.Context
import app.storytel.candidate.com.app.StorytelApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule {

    @Provides
    @Singleton
    fun provideContext(application: StorytelApp): Context {
        return application.applicationContext
    }
}