package com.goga133.oknaservice.ui.calculator

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.goga133.oknaservice.Calculator
import com.goga133.oknaservice.R
import com.goga133.oknaservice.listeners.OnPageChangeListener
import com.goga133.oknaservice.adapters.SliderAdapter
import com.goga133.oknaservice.listeners.OnSeekBarChangeListener
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView


class CalculatorFragment : Fragment() {

    private lateinit var widthSeekBar : SeekBar
    private lateinit var heightSeekBar : SeekBar

    private lateinit var widthTextView : TextView
    private lateinit var heightTextView : TextView

    private lateinit var currentWindow : Calculator.Window
    private lateinit var elements : Array<SliderAdapter.SliderItem>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val calculatorViewModel = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
        elements = calculatorViewModel.arraySliders.value ?: arrayOf()
        currentWindow = Calculator().chooseWindow(elements[0].windowId)

        val root = inflater.inflate(R.layout.fragment_calculator, container, false)

        widthSeekBar = root.findViewById(R.id.seekBar_width)
        heightSeekBar = root.findViewById(R.id.seekBar_height)

        widthTextView = root.findViewById(R.id.text_view_bar_width)
        heightTextView = root.findViewById(R.id.text_view_bar_height)

        widthSeekBar.setOnSeekBarChangeListener(OnSeekBarChangeListener(fun (progress : Int): Unit {
            widthTextView.text = "Длина: ${progress + (currentWindow.minW)} см."
        }))
        heightSeekBar.setOnSeekBarChangeListener(OnSeekBarChangeListener(fun (progress : Int): Unit {
            heightTextView.text = "Высота: ${progress + (currentWindow.minH)} см."
        }))

        // ===== Slider settings ===== //
        val sliderView: SliderView = root.findViewById(R.id.imageSlider)

        val sliderAdapter  = SliderAdapter(root.context, elements)

        sliderView.setSliderAdapter(sliderAdapter)

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)

        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.isAutoCycle = false
        sliderView.indicatorRadius = 1
        sliderView.indicatorSelectedColor = Color.WHITE
        sliderView.indicatorUnselectedColor = Color.GRAY

        sliderView.sliderPager.addOnPageChangeListener(
            OnPageChangeListener(
            fun(position: Int): Unit {

                currentWindow  = Calculator().chooseWindow(elements[position].windowId)
                heightSeekBar.max = currentWindow.maxH - currentWindow.minH
                widthSeekBar.max = currentWindow.maxW - currentWindow.minW

                heightSeekBar.progress = 0
                widthSeekBar.progress = 0

            })
        )
        sliderView.currentPagePosition = 0

        // ==== Slider Settings ==== //


        // TODO: Доделать выплавающий диалог по кнопке.
        return root

    }

}





