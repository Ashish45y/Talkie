package dev.ashish.talkie.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import dev.ashish.talkie.di.ActivityContext

@Module
class ActivityModule(private val activity: AppCompatActivity) {
    @ActivityContext
    @Provides
    fun provideContext(): Context{
        return activity
    }
}