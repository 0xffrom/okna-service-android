package com.goga133.oknaservice.models.product

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.goga133.oknaservice.models.price.TotalCost
import com.goga133.oknaservice.models.price.UserWindow


@Entity
data class Product(
    val windowType: String?,
    val windowImage : String?,
    var height: Int,
    var width: Int,
    var profileName: String?,
    val extrasTypes: String?,
    var typeGlass: String?,
    var typeHome: String?,
    var isWinInstall: Boolean?,
    var isWinDelivery: Boolean?,
    val sum: Int,
    val sumW: Int,
    val sumO: Int,
    val sumM: Int,
    val sumD: Int
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    constructor(userWindow: UserWindow, totalCost: TotalCost) : this(
        userWindow.window?.type,
        userWindow.window?.imageUrl,
        userWindow.height,
        userWindow.width,
        userWindow.profile?.name,
        userWindow.extras?.map { it -> it.name }?.joinToString { it -> "$it " },
        userWindow.typeGlass,
        userWindow.typeHome,
        userWindow.isWinInstall,
        userWindow.isWinDelivery,
        totalCost.sum,
        totalCost.sumW,
        totalCost.sumO,
        totalCost.sumM,
        totalCost.sumD
    )
}