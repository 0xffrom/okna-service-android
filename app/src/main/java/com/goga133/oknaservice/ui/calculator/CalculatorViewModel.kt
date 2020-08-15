package com.goga133.oknaservice.ui.calculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.goga133.oknaservice.core.NetworkApi
import com.goga133.oknaservice.core.NetworkService
import com.goga133.oknaservice.models.Event
import com.goga133.oknaservice.models.price.Price
import com.goga133.oknaservice.models.price.UserWindow
import com.goga133.oknaservice.models.product.Product
import com.goga133.oknaservice.models.product.ProductDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CalculatorViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = ProductDatabase.getInstance(application).productDao()

    fun insertProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        dao.insert(product)
    }

    val priceLiveData = MutableLiveData<Event<Price>>()

    fun getPrice() {
        requestWithLiveData(priceLiveData) {
            api.getPrice()
        }
    }

    private var api: NetworkApi = NetworkService.retrofitService()

    private fun <T> requestWithLiveData(
        liveData: MutableLiveData<Event<T>>,
        request: suspend () -> Call<T>
    ) {

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