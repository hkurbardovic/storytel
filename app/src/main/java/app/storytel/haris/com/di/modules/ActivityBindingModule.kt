package app.storytel.haris.com.di.modules

import app.storytel.haris.com.details.DetailsActivity
import app.storytel.haris.com.di.scopes.ActivityScoped
import app.storytel.haris.com.scrolling.ScrollingActivity
import app.storytel.haris.com.settings.SettingsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("UNUSED")
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun scrollingActivity(): ScrollingActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun detailsActivity(): DetailsActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun settingsActivity(): SettingsActivity
}