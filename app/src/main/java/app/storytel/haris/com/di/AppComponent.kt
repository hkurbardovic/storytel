package app.storytel.haris.com.di

import app.storytel.haris.com.app.StorytelApp
import app.storytel.haris.com.di.modules.ActivityBindingModule
import app.storytel.haris.com.di.modules.ContextModule
import app.storytel.haris.com.di.modules.DatabaseModule
import app.storytel.haris.com.di.modules.NetworkModule
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