package com.goga133.oknaservice.models.price

import com.google.gson.annotations.SerializedName

data class Window(
    val description: String,
    val type: String,
    val imageUrl: String,
    val minWidth: Int,
    val maxWidth: Int,
    val minHeight: Int,
    val maxHeight: Int,
    /* ================================
    N - кол-во створок
    Ng - кол-во глухих створок
    Np - кол-во поворотных створок
    No - кол-во поворотно-откидных створок
    Nh - кол-во перемычек
    D - неизвестно 0_о
    Dp - кол-во поворотных дверей
    Do - кол-во поворотно-откидных дверей
       ================================ */
    @SerializedName("n")
    val N: Int,
    @SerializedName("ng")
    val Ng: Int,
    @SerializedName("np")
    val Np: Int,
    @SerializedName("no")
    val No: Int,
    @SerializedName("nh")
    val Nh: Int,
    @SerializedName("d")
    val D: Int,
    @SerializedName("dp")
    val Dp: Int,
    @SerializedName("do")
    val Do: Int
)