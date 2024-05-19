package dev.ashish.talkie.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dev.ashish.talkie.TalkieApplication
import dev.ashish.talkie.di.ApplicationContext
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: TalkieApplication) {
    @ApplicationContext
    @Provides
    fun provideContext(): Context{
        return application
    }
    @Provides
    @Singleton
    fun provideApplication() = application
}