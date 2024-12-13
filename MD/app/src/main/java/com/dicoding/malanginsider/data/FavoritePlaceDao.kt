package com.dicoding.malanginsider.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete

@Dao
interface FavoritePlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(place: FavoritePlace)

    @Query("SELECT * FROM favorite_places")
    suspend fun getAllFavorites(): List<FavoritePlace>

    @Delete
    suspend fun deleteFavorite(place: FavoritePlace)
}
