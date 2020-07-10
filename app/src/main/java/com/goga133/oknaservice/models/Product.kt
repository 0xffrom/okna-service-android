package com.goga133.oknaservice.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity
data class Product(
    val windowId: String?,
    val windowIdResource: Int,
    val h: Int,
    val w: Int,
    val profile: String?,
    val glass: String?,
    val home: String?,
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
) : Parcelable {
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

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
        uid = parcel.readInt()
    }

    private fun getRusBool(b : Boolean) : String =
        when(b) {
            true -> "Да"
            false -> "Нет"
        }

    override fun toString(): String =
        "Окно №$windowId.\n" +
                "Длина: $w метров.\n" +
                "Высота: $h метров.\n" +
                "Профиль: $typeProfile.\n" +
                "Стеклопакет: $typeGlass.\n" +
                "Тип дома: $typeHome.\n" +
                "Подоконник: ${getRusBool(isWinSill)}.\n" +
                "Отлив: ${getRusBool(isWinTide)}.\n" +
                "Откосы: ${getRusBool(isWinSlope)}.\n" +
                "Москитная сетка: ${getRusBool(isWinGrid)}.\n" +
                "Монтажные работы: ${getRusBool(isWinInstall)}.\n" +
                "Доставка: ${getRusBool(isWinInstall)}.\n" +
                "Рассчитанная стоимость:\n" +
                "Окно: $priceW р.\n" +
                "Дополнительные опции: $priceO р.\n" +
                "Монтаж: $priceM р.\n" +
                "Итог: $priceSum р."

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(windowId)
        parcel.writeInt(windowIdResource)
        parcel.writeInt(h)
        parcel.writeInt(w)
        parcel.writeString(profile)
        parcel.writeString(glass)
        parcel.writeString(home)
        parcel.writeByte(if (isWinSill) 1 else 0)
        parcel.writeByte(if (isWinTide) 1 else 0)
        parcel.writeByte(if (isWinSlope) 1 else 0)
        parcel.writeByte(if (isWinGrid) 1 else 0)
        parcel.writeByte(if (isWinInstall) 1 else 0)
        parcel.writeByte(if (isWinDelivery) 1 else 0)
        parcel.writeInt(priceSum)
        parcel.writeInt(priceW)
        parcel.writeInt(priceO)
        parcel.writeInt(priceM)
        parcel.writeInt(priceD)
        parcel.writeInt(uid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}