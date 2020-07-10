package com.goga133.oknaservice.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity
data class Product(
    val windowId: String,
    val windowIdResource : Int,
    val h: Int,
    val w: Int,
    val profile: String,
    val glass: String,
    val home: String,
    val isWinSill: Boolean,
    val isWinTide: Boolean,
    val isWinSlope: Boolean,
    val isWinGrid: Boolean,
    val isWinInstall: Boolean,
    val isWinDelivery: Boolean,
    val priceSum: Int,
    val priceW: Int,
    val priceO: Int,
    val priceM: Int,
    val priceD: Int
) {
    constructor(
        uid: Int, windowId: String, windowIdResource : Int, h: Int, w: Int, profile: String,
        glass: String, home: String, isWinSill: Boolean, isWinTide: Boolean,
        isWinSlope: Boolean, isWinGrid: Boolean, isWinInstall: Boolean,
        isWinDelivery: Boolean, priceSum: Int, priceW: Int, priceO: Int,
        priceM: Int, priceD: Int
    ) : this(
        windowId,
        windowIdResource,
        h,
        w,
        profile,
        glass,
        home,
        isWinSill,
        isWinTide,
        isWinSlope,
        isWinGrid,
        isWinInstall,
        isWinDelivery,
        priceSum,
        priceW,
        priceO,
        priceM,
        priceD
    ) {
        this.uid = uid
    }

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    @Ignore
    private val typeProfile = when (profile) {
        "p1" -> "Бюджет"
        "p2" -> "Оптимум"
        "p3" -> "Премиум"
        else -> "Неопределён"
    }
    @Ignore
    private val typeGlass = when (glass) {
        "prOne" -> "Однокамерный"
        "prTwo" -> "Двухкамерный"
        else -> "Неопределён"
    }
    @Ignore
    private val typeHome = when (home) {
        "panel" -> "Панельный"
        "brick" -> "Кирпичный"
        else -> "Неопределён"
    }

    override fun toString(): String =
        "Окно №$windowId.\n" +
                "Длина: $w метров.\n" +
                "Высота: $h метров.\n" +
                "Профиль: $typeProfile.\n" +
                "Стеклопакет: $typeGlass.\n" +
                "Тип дома: $typeHome.\n" +
                "Подоконник: $isWinSill.\n" +
                "Отлив: $isWinTide.\n" +
                "Откосы: $isWinSlope.\n" +
                "Москитная сетка: $isWinGrid.\n" +
                "Монтажные работы: $isWinInstall.\n" +
                "Доставка: $isWinInstall.\n" +
                "Рассчитанная стоимость:\n" +
                "Окно: $priceW р.\n" +
                "Дополнительные опции: $priceO р.\n" +
                "Доставка: $priceD р.\n" +
                "Монтаж: $priceM р.\n" +
                "Итог: $priceSum р."
}