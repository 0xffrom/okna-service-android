package com.goga133.oknaservice.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.goga133.oknaservice.R
import com.goga133.oknaservice.models.price.Window
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso


class SliderAdapter(private val context: Context) :
    SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {
    private var mSliderItems: List<Window> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.model_image_slider, parent, false)
        return SliderAdapterVH(inflate)
    }

    fun renewItems(sliderItems: List<Window>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int): Window {
        return mSliderItems[position];
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {

        val window: Window = mSliderItems[position]
        viewHolder.textViewDescription.text = window.description
        viewHolder.textViewDescription.textSize = 16f
        viewHolder.textViewDescription.setTextColor(Color.WHITE)
        Picasso
            .get()
            .load(window.imageUrl)
            .into(viewHolder.imageViewBackground)
    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mSliderItems.size
    }

    class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {
        val imageViewBackground: ImageView = itemView.findViewById(R.id.iv_auto_image_slider)
        val textViewDescription: TextView = itemView.findViewById(R.id.tv_auto_image_slider)
    }

}