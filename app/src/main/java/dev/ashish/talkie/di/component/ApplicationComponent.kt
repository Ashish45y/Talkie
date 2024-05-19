package dev.ashish.talkie.di.component

import android.content.Context
import dagger.Component
import dev.ashish.talkie.TalkieApplication
import dev.ashish.talkie.di.ApplicationContext
import dev.ashish.talkie.di.module.ApplicationModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: TalkieApplication)
    @ApplicationContext
    fun getApplicationContext(): Context
}