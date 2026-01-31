package com.example.animedekho.domain.repository

import com.example.animedekho.domain.entity.AnimeBasicDetails
import com.example.animedekho.domain.entity.AnimeDetails
import kotlinx.coroutines.flow.Flow

interface TopAnimeListRepository {
     fun getTopAnimeList() : Flow<List<AnimeBasicDetails>>
     fun getAnimeDetails(animeId :Int) : Flow<AnimeDetails>

}