package com.example.animedekho.data.remote

import com.google.gson.annotations.SerializedName


data class TopAnimeListResponseModel(
    @SerializedName("data")
    val data: List<Anime>?
)

data class AnimeDetailsResponseModel(
    @SerializedName("data")
    val data: Anime?
)

data class Anime(
    @SerializedName("mal_id")
    val malId: Int?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("images")
    val images: Images?,

    @SerializedName("trailer")
    val trailer: Trailer?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("episodes")
    val episodes: Int?,

    @SerializedName("rating")
    val rating: String?,

    @SerializedName("score")
    val score: Double?,

    @SerializedName("synopsis")
    val synopsis: String?,

    @SerializedName("genres")
    val genres:List<Genre>?
)

data class Images(
    @SerializedName("jpg")
    val jpg: ImageSize?,

    @SerializedName("webp")
    val webp: ImageSize?
)

data class ImageSize(
    @SerializedName("image_url")
    val imageUrl: String?,

    @SerializedName("small_image_url")
    val smallImageUrl: String?,

    @SerializedName("large_image_url")
    val largeImageUrl: String?
)

data class Trailer(
    @SerializedName("youtube_id")
    val youtubeId: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("embed_url")
    val embedUrl: String?,

    @SerializedName("images")
    val images: TrailerImages?
)

data class TrailerImages(
    @SerializedName("image_url")
    val imageUrl: String?,

    @SerializedName("small_image_url")
    val smallImageUrl: String?,

    @SerializedName("medium_image_url")
    val mediumImageUrl: String?,

    @SerializedName("large_image_url")
    val largeImageUrl: String?,

    @SerializedName("maximum_image_url")
    val maximumImageUrl: String?
)


data class Genre(
    @SerializedName("name")
    val name: String?,
)
