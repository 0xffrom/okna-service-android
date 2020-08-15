package com.goga133.oknaservice.models.price

data class Extra(val name: String, val panel: Double, val brick: Double){
    var selected : Boolean = false
    fun getPriceByTypeHome(typeHome: String) =
        when (typeHome) {
            "panel" -> panel
            "brick" -> brick
            else -> throw UnsupportedOperationException("Выбран неверный стеклопакет: $typeHome")
        }
}