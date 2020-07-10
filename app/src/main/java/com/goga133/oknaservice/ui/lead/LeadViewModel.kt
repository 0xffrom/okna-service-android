package com.goga133.oknaservice.ui.lead

import android.app.Application
import androidx.lifecycle.*
import com.goga133.oknaservice.models.Product
import com.goga133.oknaservice.models.ProductDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LeadViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = ProductDatabase.getInstance(application).productDao()

    fun getProducts() = dao.getAll()

    fun deleteProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        dao.delete(product)
    }
}