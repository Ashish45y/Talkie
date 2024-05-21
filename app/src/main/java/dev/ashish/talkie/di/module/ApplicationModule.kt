package dev.ashish.talkie.di.module

import android.content.Context
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import dagger.Module
import dagger.Provides
import dev.ashish.talkie.TalkieApplication
import dev.ashish.talkie.data.api.ApiKeyInteceptor
import dev.ashish.talkie.data.api.NetworkService
import dev.ashish.talkie.di.ApplicationContext
import dev.ashish.talkie.di.BaseUrl
import dev.ashish.talkie.di.NetworkApiKey
import dev.ashish.talkie.utils.Constants
import dev.ashish.talkie.utils.DefaultDispatcherProvider
import dev.ashish.talkie.utils.DispatcherProvider
import dev.ashish.talkie.utils.NetworkHelper
import dev.ashish.talkie.utils.NetworkHelperImpl
import dev.ashish.talkie.utils.logger.AppLogger
import dev.ashish.talkie.utils.logger.Logger
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Provides
    @Singleton
    fun provideGsonConverterFactory() : GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }
    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelperImpl(context)
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(@NetworkApiKey apiKey: String): ApiKeyInteceptor =
        ApiKeyInteceptor(apiKey)


    @Provides
    @Singleton
    fun provideDispatcher(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun provideLogger(): Logger = AppLogger()

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = Constants.BASE_URL


    @NetworkApiKey
    @Provides
    fun provideApiKey(): String = Constants.API_KEY
}