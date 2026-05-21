package com.example.animedekho.di

import android.content.Context
import com.example.animedekho.data.repository.TopAnimeListRepositoryImplementation
import com.example.animedekho.domain.repository.TopAnimeListRepository
import com.example.animedekho.domain.usecase.TopAnimeListUseCase
import com.example.animedekho.ui.anime.view.activity.MainActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity: MainActivity){

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }
}