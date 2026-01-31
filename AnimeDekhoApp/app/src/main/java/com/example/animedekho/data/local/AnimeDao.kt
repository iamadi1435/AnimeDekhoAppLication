package com.example.animedekho.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Query("SELECT * FROM top_anime")
    fun getTopAnimeList(): Flow<List<TopAnimeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopAnime(list: List<TopAnimeEntity>)

    @Query("SELECT * FROM anime_details WHERE id = :animeId")
    fun getAnimeDetails(animeId: Int): Flow<AnimeDetailsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimeDetails(details: AnimeDetailsEntity)
}
