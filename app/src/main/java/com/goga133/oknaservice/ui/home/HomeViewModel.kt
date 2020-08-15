package com.goga133.oknaservice.ui.home

import androidx.lifecycle.MutableLiveData
import com.goga133.oknaservice.core.BaseViewModel
import com.goga133.oknaservice.models.Event
import com.goga133.oknaservice.models.News


class HomeViewModel : BaseViewModel() {

    val newsLiveData = MutableLiveData<Event<List<News>>>()

    fun getNews() {
        requestWithLiveData(newsLiveData) {
            api.getNews()
        }
    }

}