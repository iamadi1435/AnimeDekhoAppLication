package com.example.animedekho.data.mapper


import android.annotation.SuppressLint
import com.example.animedekho.data.local.AnimeDetailsEntity
import com.example.animedekho.data.local.TopAnimeEntity
import com.example.animedekho.data.remote.AnimeDetailsResponseModel
import com.example.animedekho.data.remote.Genre
import com.example.animedekho.data.remote.TopAnimeListResponseModel
import com.example.animedekho.domain.entity.AnimeBasicDetails
import com.example.animedekho.domain.entity.AnimeDetails


fun mapToTopAnimeList(response: TopAnimeListResponseModel): List<AnimeBasicDetails> {
    val topAnimeList: MutableList<AnimeBasicDetails> = mutableListOf()
    response.data?.forEach { animeItem ->
        topAnimeList.add(
            AnimeBasicDetails(
                id = animeItem.malId ?: 0,
                posterUrl = animeItem.images?.webp?.largeImageUrl ?: "",
                title = animeItem.title ?: "",
                rating = getRating(animeItem.score),
                numberOfEpisodes = getNumberOfEpisodes(animeItem.episodes)
            )
        )
    }
    return topAnimeList
}

fun mapToAnimeDetails(response: AnimeDetailsResponseModel): AnimeDetails {
    return AnimeDetails(
        id = response.data?.malId ?: 0,
        title =  response.data?.title ?: "",
        videoUrl = response.data?.trailer?.embedUrl ?: "",
        thumbnailUrl = response.data?.images?.webp?.largeImageUrl ?: "",
        rating = getRating(response.data?.score),
        episodes = getNumberOfEpisodes(response.data?.episodes),
        synopsis = response.data?.synopsis ?: "",
        genres = getGenresList(response.data?.genres)
    )
}

@SuppressLint("DefaultLocale")
fun getRating(score: Double?): String {
    if (score == null || score == 0.0) return ""
    val formattedScore = String.format("%.1f", score)
    return "Rating: $formattedScore/10"
}

fun getGenresList(genres: List<Genre>?): List<String> {
    val genreList = mutableListOf<String>()
    genres?.forEach {
        genreList.add(
            it.name ?: ""
        )
    }
    return genreList
}


fun getNumberOfEpisodes(episodes: Int?): String {
    return episodes?.let {
        "Episode${if (it > 1) "s" else ""}: $it"
    } ?: ""
}

fun AnimeBasicDetails.toEntity() = TopAnimeEntity(
    id = id,
    posterUrl = posterUrl,
    title = title,
    rating = rating,
    numberOfEpisodes = numberOfEpisodes,
    lastUpdated = System.currentTimeMillis()
)

fun AnimeDetails.toEntity() = AnimeDetailsEntity(
    id = id,
    title = title,
    videoUrl = videoUrl,
    thumbnailUrl = thumbnailUrl,
    rating = rating,
    episodes = episodes,
    synopsis = synopsis,
    genres = genres.joinToString(","),
    lastUpdated = System.currentTimeMillis()
)

fun TopAnimeEntity.toDomain() = AnimeBasicDetails(
    id = id,
    posterUrl = posterUrl,
    title = title,
    rating = rating,
    numberOfEpisodes = numberOfEpisodes
)

fun AnimeDetailsEntity.toDomain() = AnimeDetails(
    id = id,
    title = title,
    videoUrl = videoUrl,
    thumbnailUrl = thumbnailUrl,
    rating = rating,
    episodes = episodes,
    synopsis = synopsis,
    genres = genres.split(",")
)


