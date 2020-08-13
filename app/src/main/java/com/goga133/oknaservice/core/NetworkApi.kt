package com.goga133.oknaservice.core

import com.goga133.oknaservice.models.News
import retrofit2.Call
import retrofit2.http.GET

interface NetworkApi {
    @GET("news")
    fun getNews() : Call<List<News>>
}