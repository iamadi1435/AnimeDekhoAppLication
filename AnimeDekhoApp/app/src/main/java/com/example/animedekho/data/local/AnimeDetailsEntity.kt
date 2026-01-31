package com.example.animedekho.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_details")
data class AnimeDetailsEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val videoUrl: String,
    val thumbnailUrl: String,
    val rating: String,
    val episodes: String,
    val synopsis: String,
    val genres: String,
    val lastUpdated: Long
)

