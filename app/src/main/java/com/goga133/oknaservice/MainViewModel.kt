package com.goga133.oknaservice

import androidx.lifecycle.MutableLiveData
import com.goga133.oknaservice.core.BaseViewModel
import com.goga133.oknaservice.models.Event
import com.goga133.oknaservice.models.info.Info

class MainViewModel : BaseViewModel() {
    val infoLiveData = MutableLiveData<Event<Info>>()

    fun getInfo() {
        requestWithLiveData(infoLiveData) {
            api.getInfo()
        }
    }

}