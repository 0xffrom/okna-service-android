package com.goga133.oknaservice.core

import com.goga133.oknaservice.models.Contact
import com.goga133.oknaservice.models.News
import com.goga133.oknaservice.models.info.Info
import com.goga133.oknaservice.models.price.Price
import retrofit2.Call
import retrofit2.http.GET

interface NetworkApi {
    @GET("news")
    fun getNews() : Call<List<News>>

    @GET("info")
    fun getInfo() : Call<Info>

    @GET("contacts")
    fun getContacts() : Call<List<Contact>>

    @GET("price")
    fun getPrice() : Call<Price>
}