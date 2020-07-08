package com.goga133.oknaservice.listeners

import com.smarteist.autoimageslider.SliderPager

class OnPageChangeListener(val callback: (Int) -> Unit) : SliderPager.OnPageChangeListener {

    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int)  {
        callback(position)
    }

}