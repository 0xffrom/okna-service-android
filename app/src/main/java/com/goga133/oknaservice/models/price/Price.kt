package com.goga133.oknaservice.models.price

data class Price(
    val profiles: List<Profile>,
    val windows: List<Window>,
    val extras: List<Extra>,
    val additionalPrice: AdditionalPrice
)