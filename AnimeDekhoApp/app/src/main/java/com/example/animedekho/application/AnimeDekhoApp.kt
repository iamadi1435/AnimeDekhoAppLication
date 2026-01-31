package com.example.animedekho.application

import android.app.Application
import androidx.room.Room
import com.example.animedekho.data.local.AnimeDatabase
import com.example.animedekho.data.remote.NetworkHelper
import com.example.animedekho.data.repository.TopAnimeListRepositoryImplementation


class AnimeDekhoApp : Application() {
    lateinit var repository: TopAnimeListRepositoryImplementation
        private set

    override fun onCreate() {
        super.onCreate()

        val db = Room.databaseBuilder(
            this,
            AnimeDatabase::class.java,
            "anime_db"
        ).build()

        repository = TopAnimeListRepositoryImplementation(
            db.animeDao(),
            NetworkHelper(this)
        )
    }
}
