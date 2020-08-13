package com.goga133.oknaservice.models

data class Window(
    val Description: String,
    val ImageUrl: String,
    val MinWidth: Double,
    val MaxWidth: Double,
    val MinHeight: Double,
    val MaxHeight: Double,

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

    val N: Int,
    val Ng: Int,
    val Np: Int,
    val No: Int,
    val Nh: Int,
    val D: Int,
    val Dp: Int,
    val Do: Int
)