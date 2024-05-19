package dev.ashish.talkie

import android.app.Application
import dev.ashish.talkie.di.component.ApplicationComponent
import dev.ashish.talkie.di.component.DaggerApplicationComponent
import dev.ashish.talkie.di.module.ApplicationModule

class TalkieApplication : Application() {
    lateinit var applicationComponent : ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        injectDepenedencies()
    }

    private fun injectDepenedencies() {
        applicationComponent =
            DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        applicationComponent.inject(this)
    }

}