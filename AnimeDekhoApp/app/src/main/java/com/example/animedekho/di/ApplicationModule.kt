package com.example.animedekho.di

import android.content.Context
import androidx.room.Room
import com.example.animedekho.application.AnimeDekhoApp
import com.example.animedekho.data.local.AnimeDatabase
import com.example.animedekho.data.remote.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class ApplicationModule (private val application: AnimeDekhoApp){

    @ApplicationContext
    @Provides
    fun provideApplicationContext(): Context {
        return application
    }
    @Singleton
    @Provides
    fun provideLoggingInterceptor() : HttpLoggingInterceptor{
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(provideLoggingInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient
    ): ApiService {
        return Retrofit
            .Builder()
            .baseUrl("https://api.jikan.moe/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)

    }

    @Singleton
    @Provides
    fun provideDataBase(
       @ApplicationContext applicationContext: Context
    ): AnimeDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AnimeDatabase::class.java,
            "anime_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideAnimeDao(database: AnimeDatabase) = database.animeDao()

}