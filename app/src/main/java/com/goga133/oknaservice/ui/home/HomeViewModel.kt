package com.goga133.oknaservice.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goga133.oknaservice.core.BaseViewModel
import com.goga133.oknaservice.core.Event
import com.goga133.oknaservice.models.News


class HomeViewModel : BaseViewModel() {

    val newsLiveData = MutableLiveData<Event<List<News>>>()

    fun getNews() {
        requestWithLiveData(newsLiveData) {
            api.getNews()
        }
    }

}