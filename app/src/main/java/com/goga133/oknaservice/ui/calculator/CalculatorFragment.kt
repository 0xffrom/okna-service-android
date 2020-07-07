package com.goga133.oknaservice.ui.calculator

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.goga133.oknaservice.R
import com.goga133.oknaservice.adapters.OfficesAdapter
import com.goga133.oknaservice.adapters.SliderAdapter
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView


class CalculatorFragment : Fragment() {

    private lateinit var calculatorViewModel: CalculatorViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        calculatorViewModel = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_calculator, container, false)

        val sliderView: SliderView = root.findViewById(R.id.imageSlider)

        calculatorViewModel.arraySliders.observe(viewLifecycleOwner,
            Observer { sliderView.setSliderAdapter( SliderAdapter(root.context, it))})

        sliderView.sliderPager.currentItem = 8
        sliderView.currentPagePosition = 8
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)

        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.isAutoCycle = false
        sliderView.indicatorSelectedColor = Color.WHITE
        sliderView.indicatorUnselectedColor = Color.GRAY

        // TODO: Пофиксить багу с количеством кружочков.
        // TODO: Сделать дискрипшен к фоточкам.

        return root
    }
}