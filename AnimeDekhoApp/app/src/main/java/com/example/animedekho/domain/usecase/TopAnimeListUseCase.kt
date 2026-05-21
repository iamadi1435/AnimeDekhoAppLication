package com.example.animedekho.domain.usecase

import com.example.animedekho.data.repository.TopAnimeListRepositoryImplementation
import com.example.animedekho.di.ActivityScope
import com.example.animedekho.domain.entity.AnimeBasicDetails
import com.example.animedekho.domain.entity.AnimeDetails
import com.example.animedekho.domain.repository.TopAnimeListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopAnimeListUseCase @Inject constructor(
    private val repository: TopAnimeListRepositoryImplementation
) {
    fun getTopAnimeList(): Flow<List<AnimeBasicDetails>> =
        repository.getTopAnimeList()

    fun getAnimeDetails(animeId: Int): Flow<AnimeDetails> =
        repository.getAnimeDetails(animeId)

    suspend fun refreshTopAnime() {
        repository.refreshTopAnime()
    }

    suspend fun refreshAnimeDetails(id: Int) {
        repository.refreshAnimeDetails(id)
    }
}

