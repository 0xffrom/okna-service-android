package com.goga133.oknaservice.models.info

import com.goga133.oknaservice.models.Contact

data class Info(
    val id: Int,
    val phoneNumber: String,
    val email: String,
    val supportEmail: String,
    val web: String
)