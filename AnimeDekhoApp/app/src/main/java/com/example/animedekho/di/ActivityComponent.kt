package com.example.animedekho.di

import com.example.animedekho.ui.anime.view.activity.MainActivity
import dagger.Component
import javax.inject.Singleton


@ActivityScope
@Component( dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    // consumers
    fun injectToActivity(activity: MainActivity)
}