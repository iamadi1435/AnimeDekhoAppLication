package com.example.animedekho.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("top/anime")
    suspend fun getTopAnimeList(): TopAnimeListResponseModel
    @GET("anime/{animeId}")
    suspend fun getAnimeDetails(
        @Path("animeId") animeId:Int
    ): AnimeDetailsResponseModel
}