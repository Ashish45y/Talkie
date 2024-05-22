package dev.ashish.talkie.di.component

import dagger.Component
import dev.ashish.talkie.ui.MainActivity
import dev.ashish.talkie.ui.SplashActivity
import dev.ashish.talkie.di.ActivityScope
import dev.ashish.talkie.di.module.ActivityModule
import dev.ashish.talkie.repository.NowPlayingRepo

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(acticity: SplashActivity)
    fun inject(acticity: MainActivity)
    fun nowPlayingRepo() : NowPlayingRepo
}