package app.storytel.candidate.com.di

import app.storytel.candidate.com.app.StorytelApp
import app.storytel.candidate.com.di.modules.ActivityBindingModule
import app.storytel.candidate.com.di.modules.ContextModule
import app.storytel.candidate.com.di.modules.DatabaseModule
import app.storytel.candidate.com.di.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ContextModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        ActivityBindingModule::class
    ]
)
interface AppComponent : AndroidInjector<StorytelApp> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance storytelApp: StorytelApp): AppComponent
    }
}