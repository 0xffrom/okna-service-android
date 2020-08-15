package com.goga133.oknaservice.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goga133.oknaservice.models.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseViewModel : ViewModel() {

    var api: NetworkApi = NetworkService.retrofitService()

    fun <T> requestWithLiveData(
        liveData: MutableLiveData<Event<T>>,
        request: suspend () -> Call<T>) {

        liveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                request.invoke().enqueue(object : Callback<T> {
                    override fun onFailure(call: Call<T>, t: Throwable) {
                        t.printStackTrace()
                        liveData.postValue(Event.error(t))
                    }

                    override fun onResponse(
                        call: Call<T>,
                        response: Response<T>
                    ) {
                        if (response.isSuccessful) {
                            liveData.postValue(Event.success(response.body()))
                        }
                        else {
                            liveData.postValue(Event.error(Throwable(response.errorBody()?.string())))
                        }
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
                liveData.postValue(Event.error(null))
            }
        }
    }

}