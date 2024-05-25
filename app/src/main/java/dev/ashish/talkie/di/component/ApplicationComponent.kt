package dev.ashish.talkie.di.component

import android.content.Context
import dagger.Component
import dev.ashish.talkie.TalkieApplication
import dev.ashish.talkie.data.api.NetworkService
import dev.ashish.talkie.di.ApplicationContext
import dev.ashish.talkie.di.module.ApplicationModule
import dev.ashish.talkie.utils.DispatcherProvider
import dev.ashish.talkie.utils.NetworkHelper
import dev.ashish.talkie.utils.logger.Logger
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: TalkieApplication)
    @ApplicationContext
    fun getApplicationContext(): Context

    fun getNetworkHelper() : NetworkHelper

    fun getDispatcherProvider(): DispatcherProvider

    fun getLoggerProvider(): Logger

    fun getNetworkService(): NetworkService
}