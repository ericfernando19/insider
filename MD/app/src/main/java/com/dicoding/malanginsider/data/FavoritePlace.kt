package com.dicoding.malanginsider.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_places")
data class FavoritePlace(
    @PrimaryKey val id: String,
    val nama: String,
    val deskripsi: String,
    val alamat: String,
    val urlGambar: String,
    val rating: Float
)
