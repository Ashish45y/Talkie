package dev.ashish.talkie.di.component

import dagger.Component
import dev.ashish.talkie.MainActivity
import dev.ashish.talkie.SplashActivity
import dev.ashish.talkie.di.ActivityScope
import dev.ashish.talkie.di.module.ActivityModule

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(acticity: SplashActivity)
    fun inject(acticity: MainActivity)
}