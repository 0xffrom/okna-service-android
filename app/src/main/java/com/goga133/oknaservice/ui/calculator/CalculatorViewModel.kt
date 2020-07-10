package com.goga133.oknaservice.ui.calculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.goga133.oknaservice.R
import com.goga133.oknaservice.adapters.SliderAdapter
import com.goga133.oknaservice.models.Product
import com.goga133.oknaservice.models.ProductDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculatorViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = ProductDatabase.getInstance(application).productDao()

    fun getProduct() = viewModelScope.launch(Dispatchers.IO) {
        dao.getAll()
    }

    fun insertProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        dao.insertAll(product)
    }

    private val _arraySliders = MutableLiveData<Array<SliderAdapter.SliderItem>>().apply {
        value = arrayOf(
            SliderAdapter.SliderItem(
                R.drawable.window1_1,
                "1-1", "Одностворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window1_2,
                "1-2", "Одностворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window1_3,
                "1-3", "Одностворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window1_4,
                "1-4", "Одностворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window1_5,
                "1-5", "Одностворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window1_6,
                "1-6", "Одностворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_1,
                "2-1", "Двухстворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_2,
                "2-2", "Двухстворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_3,
                "2-3", "Двухстворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_4,
                "2-4", "Двухстворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_5,
                "2-5", "Двухстворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_6,
                "2-6", "Двухстворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_7,
                "2-7", "Двухстворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_8,
                "2-8", "Двухстворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_9,
                "2-9", "Двухстворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window3_1,
                "3-1", "Трехстворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window3_2,
                "3-2", "Трехстворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window3_3,
                "3-3", "Трехстворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window3_4,
                "3-4", "Трехстворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window3_5,
                "3-5", "Трехстворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window3_6,
                "3-6", "Трехстворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window3_7,
                "3-7", "Трехстворчатое окно"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window4_1,
                "4-1", "Балконный блок"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window4_2,
                "4-2", "Балконный блок"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window4_3,
                "4-3", "Балконный блок"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window4_4,
                "4-4", "Балконный блок"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window4_5,
                "4-5", "Балконный блок"
            )
        )
    }

    val arraySliders: LiveData<Array<SliderAdapter.SliderItem>> = _arraySliders





}