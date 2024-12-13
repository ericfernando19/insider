package com.dicoding.malanginsider.data.retrofit


import com.dicoding.malanginsider.data.pref.RecommendationsItem
import com.dicoding.malanginsider.data.pref.ResponseWisata
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("recommend")
    fun getRecommendations(@Body request: com.dicoding.malanginsider.data.pref.RequestBody): Call<ResponseWisata<RecommendationsItem>>
}
