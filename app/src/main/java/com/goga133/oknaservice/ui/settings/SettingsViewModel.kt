package com.goga133.oknaservice.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goga133.oknaservice.BuildConfig

class SettingsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Версия приложения\n${BuildConfig.VERSION_NAME}\n\n" +
                "Политика конфиденциальности\n\n" +
                "Поддержка\nandroid@okna-servise.com"
    }
    val text: LiveData<String> = _text
}