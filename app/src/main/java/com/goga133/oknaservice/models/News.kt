package com.goga133.oknaservice.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class News(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("imageUrl") var imageUrl: String,
    @SerializedName("content") var content: String) : Serializable