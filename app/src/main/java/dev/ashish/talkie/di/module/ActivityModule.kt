package dev.ashish.talkie.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import dagger.Module
import dagger.Provides
import dev.ashish.talkie.R
import dev.ashish.talkie.di.ActivityContext
import dev.ashish.talkie.repository.NowPlayingRepo
import dev.ashish.talkie.ui.NowPlayingViewModel
import dev.ashish.talkie.ui.SliderAdapter
import dev.ashish.talkie.ui.base.ViewModelProviderFactory
import dev.ashish.talkie.utils.DispatcherProvider
import dev.ashish.talkie.utils.NetworkHelper
import dev.ashish.talkie.utils.logger.Logger

@Module
class ActivityModule(private val activity: AppCompatActivity) {
    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideNowPlayingViewModel(
        nowPlayingRepo: NowPlayingRepo,
        dispatcherProvider: DispatcherProvider,
        networkHelper: NetworkHelper,
        logger: Logger
    ): NowPlayingViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(NowPlayingViewModel::class) {
            NowPlayingViewModel(
                nowPlayingRepo, dispatcherProvider, networkHelper, logger
            )
        })[NowPlayingViewModel::class.java]
    }


    @Provides
    fun provideSliderAdapter(): SliderAdapter {
        return SliderAdapter(ArrayList())
    }
}