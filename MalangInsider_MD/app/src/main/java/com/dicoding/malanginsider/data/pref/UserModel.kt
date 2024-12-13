package com.dicoding.malanginsider.data.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)
data class RegisterResponse(
    val error: Boolean,
    val success: Boolean,
    val message: String? = null
)

data class LoginResponse(
    val error: Boolean,
    val message: String?,
    val loginResult: LoginResult?
)

data class LoginResult(
    val userId: String,
    val name: String,
    val token: String?
)

@Parcelize
data class TempatWisata(
    val id: String ="",
    val nama: String,
    val rating: Double,
    val ulasan: Int,
    val kategori: String,
    val deskripsi: String,
    val kota: String,
    val alamat: String,
    val kodePos: String,
    val urlgambar: String,
    val url: String,
    val latitude: Double,
    val longitude: Double,
    val telepon: String,
    val jamBuka: List<JamBuka>,
    var isFavorite: Boolean = false
) : Parcelable


@Parcelize
data class JamBuka(
    val hari: String,
    val jam: String
) : Parcelable

data class Rekomendasi(
    val name: String,
    val address: String,
    val distance: Double
)

@Parcelize
data class Penginapan(
    val placeId: String,
    val name: String,
    val address: String,
    val category: String,
    val city: String,
    val reviewsCount: Int,
    val totalScore: Double,
    val hotelStars: String,
    val postalCode: String,
    val imageUrl: String,
    val url: String,
    val latitude: Double,
    val longitude: Double,
    var distance: Double = 0.0

) : Parcelable

@Parcelize
data class Restoran(
    val title: String,
    val categories: String,
    val description: String,
    val address: String,
    val kgmid: String,
    val latitude: Double,
    val longitude: Double,
    val neighborhood: String,
    val reviewsCount: Int,
    val totalScore: Float,
    val url: String,
    val imageUrl: String,
    val openingHours: Map<String, String>,
    val permanentlyClosed: Boolean,
    var distance: Double = 0.0
) : Parcelable





