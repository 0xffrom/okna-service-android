package com.goga133.oknaservice.models.price

// sum - Общая сумма
// sumW - Стоимость изделия
// sumO - Стоимость опций
// sumM - Стоимость монтажа
// sumD - Стоимость доставк
data class TotalCost(
    val sum: Int,
    val sumW: Int,
    val sumO: Int,
    val sumM: Int,
    val sumD: Int
)