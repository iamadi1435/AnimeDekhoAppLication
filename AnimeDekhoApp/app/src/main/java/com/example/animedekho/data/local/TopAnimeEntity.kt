package com.example.animedekho.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_anime")
data class TopAnimeEntity(
    @PrimaryKey val id: Int,
    val posterUrl: String,
    val title: String,
    val rating: String,
    val numberOfEpisodes: String,
    val lastUpdated: Long
)

