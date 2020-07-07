package com.goga133.oknaservice.ui.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goga133.oknaservice.R
import com.goga133.oknaservice.adapters.SliderAdapter

class CalculatorViewModel : ViewModel() {

    private val _arraySliders = MutableLiveData<Array<SliderAdapter.SliderItem>>().apply {
        value = arrayOf(
            SliderAdapter.SliderItem(
                R.drawable.window1_1,
                "1-1"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window1_2,
                "1-2"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window1_3,
                "1-3"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window1_4,
                "1-4"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window1_5,
                "1-5"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window1_6,
                "1-6"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_1,
                "2-1"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_2,
                "2-2"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_3,
                "2-3"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_4,
                "2-4"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_5,
                "2-5"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_6,
                "2-6"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_7,
                "2-7"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_8,
                "2-8"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window2_9,
                "2-9"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window3_1,
                "3-1"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window3_2,
                "3-2"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window3_3,
                "3-3"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window3_4,
                "3-4"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window3_5,
                "3-5"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window3_6,
                "3-6"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window3_7,
                "3-7"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window4_1,
                "4-1"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window4_2,
                "4-2"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window4_3,
                "4-3"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window4_4,
                "4-4"
            ),
            SliderAdapter.SliderItem(
                R.drawable.window4_5,
                "4-5"
            )
        )
    }

    val arraySliders: LiveData<Array<SliderAdapter.SliderItem>> = _arraySliders
}