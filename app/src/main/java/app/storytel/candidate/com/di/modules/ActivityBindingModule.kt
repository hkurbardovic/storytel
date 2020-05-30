package app.storytel.candidate.com.di.modules

import app.storytel.candidate.com.details.DetailsActivity
import app.storytel.candidate.com.di.scopes.ActivityScoped
import app.storytel.candidate.com.scrolling.ScrollingActivity
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
}