package com.goga133.oknaservice.models.price

data class Profile(
    val name: String,
    val description: String,
    val prR: Double,
    val prH: Double,
    val prSp: Double,
    val prSo: Double,
    val prOne: Double,
    val prTwo: Double
) {
    fun getPriceGlassUnit(glassUnit: String) =
        when (glassUnit) {
            "prOne" -> prOne
            "prTwo" -> prTwo
            else -> throw UnsupportedOperationException("Выбран неверный стеклопакет: $glassUnit")
        }
}