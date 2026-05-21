package com.example.animedekho.di

import android.app.Application
import android.content.Context
import com.example.animedekho.application.AnimeDekhoApp
import com.example.animedekho.data.local.AnimeDao
import com.example.animedekho.data.local.AnimeDatabase
import com.example.animedekho.data.remote.ApiService
import com.example.animedekho.data.remote.NetworkHelper
import com.example.animedekho.data.repository.TopAnimeListRepositoryImplementation
import com.example.animedekho.domain.usecase.TopAnimeListUseCase
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    // consumers
    fun injectToApplicationClass(application: AnimeDekhoApp)

    // expose methods

    @ApplicationContext
    fun getApplicationContext() : Context

    fun getApiService (): ApiService
    fun getAnimeDatabase () : AnimeDatabase
    fun getAnimeDao(): AnimeDao
    fun provideNetworkHelper( ): NetworkHelper

    fun provideUseCase(): TopAnimeListUseCase

    fun provideRepoImpl(): TopAnimeListRepositoryImplementation
}