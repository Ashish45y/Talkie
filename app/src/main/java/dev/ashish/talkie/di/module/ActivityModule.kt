package dev.ashish.talkie.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dev.ashish.talkie.di.ActivityContext
import dev.ashish.talkie.repository.NowPlayingRepo
import dev.ashish.talkie.ui.adapater.PopularMovieAdapter
import dev.ashish.talkie.ui.viewmodel.NowPlayingViewModel
import dev.ashish.talkie.ui.adapater.SliderAdapter
import dev.ashish.talkie.ui.adapater.UpcomingMovieAdapter
import dev.ashish.talkie.ui.base.ViewModelProviderFactory
import dev.ashish.talkie.ui.viewmodel.PopularMovieViewModel
import dev.ashish.talkie.ui.viewmodel.UpcomingMovieViewModel
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
    fun providePopularMovieViewModel(
        nowPlayingRepo: NowPlayingRepo,
        dispatcherProvider: DispatcherProvider,
        networkHelper: NetworkHelper,
        logger: Logger
    ): PopularMovieViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(PopularMovieViewModel::class) {
            PopularMovieViewModel(
                nowPlayingRepo, dispatcherProvider, networkHelper, logger
            )
        })[PopularMovieViewModel::class.java]
    }

    @Provides
    fun provideUpcomingMovieViewModel(
        nowPlayingRepo: NowPlayingRepo,
        dispatcherProvider: DispatcherProvider,
        networkHelper: NetworkHelper,
        logger: Logger
    ): UpcomingMovieViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(UpcomingMovieViewModel::class) {
            UpcomingMovieViewModel(
                nowPlayingRepo, dispatcherProvider, networkHelper, logger
            )
        })[UpcomingMovieViewModel::class.java]
    }

    @Provides
    fun provideSliderAdapter(): SliderAdapter {
        return SliderAdapter(ArrayList())
    }

    @Provides
    fun providePopularMovieAdapter(): PopularMovieAdapter {
        return PopularMovieAdapter(ArrayList())
    }

    @Provides
    fun provideUpcomingMovieAdapter(): UpcomingMovieAdapter {
        return UpcomingMovieAdapter(ArrayList())
    }
}