package com.example.animedekho.domain.usecase

import com.example.animedekho.data.repository.TopAnimeListRepositoryImplementation
import com.example.animedekho.domain.entity.AnimeBasicDetails
import com.example.animedekho.domain.entity.AnimeDetails
import com.example.animedekho.domain.repository.TopAnimeListRepository
import kotlinx.coroutines.flow.Flow
class TopAnimeListUseCase(
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

