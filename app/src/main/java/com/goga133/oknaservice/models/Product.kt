package com.goga133.oknaservice.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity
data class Product(@PrimaryKey val uid: Int,
                   val windowId : String,
                   val h: Int,
                   val w: Int,
                   @Ignore private val profile: String,
                   @Ignore private val glass: String,
                   @Ignore private val home: String,
                   val isWinSill: Boolean,
                   val isWinTide: Boolean,
                   val isWinSlope: Boolean,
                   val isWinGrid: Boolean,
                   val isWinInstall: Boolean,
                   val isWinDelivery: Boolean,
                   @Ignore private val price : Calculator.SummaryPrice)
{
    val priceAll = price.sum
    val priceWindow = price.sumW
    val priceOptions = price.sumO
    val priceInstall = price.sumM
    val priceDelivery = price.sumD

    val typeProfile = when(profile){
        "p1" -> "Бюджет"
        "p2" -> "Оптимум"
        "p3" -> "Премиум"
        else -> "Неопределён"
    }
    val typeGlass = when(glass){
        "prOne" -> "Однокамерный"
        "prTwo" -> "Двухкамерный"
        else -> "Неопределён"
    }
    val typeHome = when(home){
        "panel" -> "Панельный"
        "brick" -> "Кирпичный"
        else -> "Неопределён"
    }

    @ColumnInfo(name = "description")
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
            "Окно: ${price.sumW} р.\n" +
            "Дополнительные опции: ${price.sumO} р.\n" +
            "Доставка: ${price.sumD} р.\n" +
            "Монтаж: ${price.sumM} р.\n" +
            "Итог: ${price.sum} р."
}