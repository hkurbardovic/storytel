package app.storytel.haris.com.app

import app.storytel.haris.com.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class StorytelApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}