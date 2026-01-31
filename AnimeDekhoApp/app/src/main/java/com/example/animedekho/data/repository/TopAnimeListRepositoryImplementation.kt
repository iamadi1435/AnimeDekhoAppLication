package com.example.animedekho.data.repository

import com.example.animedekho.data.local.AnimeDao
import com.example.animedekho.data.mapper.mapToAnimeDetails
import com.example.animedekho.data.mapper.mapToTopAnimeList
import com.example.animedekho.data.mapper.toDomain
import com.example.animedekho.data.mapper.toEntity
import com.example.animedekho.data.remote.NetworkHelper
import com.example.animedekho.data.remote.RetrofitInstance
import com.example.animedekho.domain.entity.AnimeBasicDetails
import com.example.animedekho.domain.entity.AnimeDetails
import com.example.animedekho.domain.repository.TopAnimeListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class TopAnimeListRepositoryImplementation(
    private val dao: AnimeDao,
    private val networkHelper: NetworkHelper
) : TopAnimeListRepository {
    override fun getTopAnimeList(): Flow<List<AnimeBasicDetails>> =
        dao.getTopAnimeList()
            .map { list -> list.map { it.toDomain() } }

    override fun getAnimeDetails(animeId: Int): Flow<AnimeDetails> =
        dao.getAnimeDetails(animeId)
            .filterNotNull()
            .map { it.toDomain() }
    suspend fun refreshTopAnime() {
        if (!networkHelper.isNetworkConnected()) return

        val response = RetrofitInstance.api.getTopAnimeList()
        val mapped = mapToTopAnimeList(response)
        dao.insertTopAnime(mapped.map { it.toEntity() })
    }
    suspend fun refreshAnimeDetails(animeId: Int) {
        if (!networkHelper.isNetworkConnected()) return

        val response = RetrofitInstance.api.getAnimeDetails(animeId)
        val mapped = mapToAnimeDetails(response)
        dao.insertAnimeDetails(mapped.toEntity())
    }
}
