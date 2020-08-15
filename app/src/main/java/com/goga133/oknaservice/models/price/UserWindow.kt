package com.goga133.oknaservice.models.price

import com.goga133.oknaservice.models.price.AdditionalPrice
import com.goga133.oknaservice.models.price.Extra
import com.goga133.oknaservice.models.price.Profile
import com.goga133.oknaservice.models.price.Window

/// Готовое окно с параметрами, установленными пользователем:
data class UserWindow(val window: Window,
                      var height: Int,
                      var width: Int,
                      var profile: Profile,
                      val additionalPrice: AdditionalPrice,
                      val extras: List<Extra>,
                      var typeGlass: String,
                      var typeHome: String,
                      var isWinInstall: Boolean,
                      var isWinDelivery: Boolean)