package com.example.animedekho.domain.entity

data class TopAnimeList(
    val topAnimeList : List<AnimeBasicDetails> = emptyList()
)

data class AnimeBasicDetails(
    val id:Int = 0,
    val title:String = "",
    val numberOfEpisodes :String = "",
    val rating:String = "",
    val posterUrl :String = ""
)

data class AnimeDetails(
    val id: Int = 0,
    val title: String = "",
    val episodes: String = "",
    val rating: String = "",
    val synopsis: String = "",
    val thumbnailUrl: String = "",
    val videoUrl: String = "",
    val genres: List<String> = emptyList(),
    val mainCast: List<CastMember> = emptyList()
)

data class CastMember(
    val name: String,
    val imageUrl: String
)
