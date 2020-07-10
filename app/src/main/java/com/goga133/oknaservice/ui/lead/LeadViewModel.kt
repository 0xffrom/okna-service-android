package com.goga133.oknaservice.ui.lead

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goga133.oknaservice.models.Product
import com.goga133.oknaservice.models.ProductDatabase

class LeadViewModel(application: Application) : ViewModel() {

    private val productDatabase : ProductDatabase = ProductDatabase.getInstance(application)
    private val products : LiveData<List<Product>> = productDatabase.productDao().getAll()

    fun getProduct() = products


    private val _text = MutableLiveData<String>().apply {
        value = "Оформление заказа"
    }
    val text: LiveData<String> = _text
}