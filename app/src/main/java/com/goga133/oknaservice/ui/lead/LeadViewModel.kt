package com.goga133.oknaservice.ui.lead

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LeadViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Оформление заказа"
    }
    val text: LiveData<String> = _text
}