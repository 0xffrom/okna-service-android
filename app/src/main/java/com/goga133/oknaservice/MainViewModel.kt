package com.goga133.oknaservice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _siteUrl = MutableLiveData<String>().apply {
        value = "https://www.okna-servise.com/"
    }
    private val _phoneNumber = MutableLiveData<String>().apply {
        value = "+74955056514"
    }
    private val _emailAddress = MutableLiveData<String>().apply {
        value = "os@okna-servise.com"
    }

    val siteUrl: LiveData<String> = _siteUrl
    val phoneNumber: LiveData<String> = _phoneNumber
    val emailAddress: LiveData<String> = _emailAddress

}