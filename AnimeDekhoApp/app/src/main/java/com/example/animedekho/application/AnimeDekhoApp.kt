package com.example.animedekho.application

import android.app.Application
import com.example.animedekho.di.ApplicationComponent
import com.example.animedekho.di.ApplicationModule
import com.example.animedekho.di.DaggerApplicationComponent


class AnimeDekhoApp : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))  // ← pass Application here
            .build()

        applicationComponent.injectToApplicationClass(this)
    }
}
