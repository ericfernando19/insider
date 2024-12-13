package com.dicoding.malanginsider.data.pref

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class ResponseWisata<T>(


    @field:SerializedName("recommendations")
    val recommendations: List<RecommendationsItem>
)


data class RequestBody(
    @SerializedName("input") val name: String,
)


@Parcelize
data class RecommendationsItem(


    @field:SerializedName("openingHours/6/day")
    val openingHours6Day: String,


    @field:SerializedName("openingHours/1/day")
    val openingHours1Day: String,


    @field:SerializedName("city")
    val city: String,


    @field:SerializedName("openingHours/1/hours")
    val openingHours1Hours: String,


    @field:SerializedName("openingHours/6/hours")
    val openingHours6Hours: String,


    @field:SerializedName("latitude")
    val latitude: String,


    @field:SerializedName("postalCode")
    val postalCode: Double,


    @field:SerializedName("placeId")
    val placeId: Int,


    @field:SerializedName("rating")
    val rating: String,


    @field:SerializedName("description")
    val description: String,


    @field:SerializedName("openingHours/4/hours")
    val openingHours4Hours: String,


    @field:SerializedName("openingHours/2/day")
    val openingHours2Day: String,


    @field:SerializedName("review")
    val review: Int,


    @field:SerializedName("imageUrl")
    val imageUrl: String,


    @field:SerializedName("openingHours/2/hours")
    val openingHours2Hours: String,


    @field:SerializedName("longitude")
    val longitude: String,


    @field:SerializedName("address")
    val address: String,


    @field:SerializedName("openingHours/4/day")
    val openingHours4Day: String,


    @field:SerializedName("openingHours/5/hours")
    val openingHours5Hours: String,


    @field:SerializedName("openingHours/3/day")
    val openingHours3Day: String,


    @field:SerializedName("openingHours/0/hours")
    val openingHours0Hours: String,


    @field:SerializedName("url")
    val url: String,


    @field:SerializedName("openingHours/0/day")
    val openingHours0Day: String,


    @field:SerializedName("openingHours/3/hours")
    val openingHours3Hours: String,


    @field:SerializedName("phone")
    val phone: String,


    @field:SerializedName("openingHours/5/day")
    val openingHours5Day: String,


    @field:SerializedName("name")
    val name: String,


    @field:SerializedName("category")
    val category: String,


    ) : Parcelable
