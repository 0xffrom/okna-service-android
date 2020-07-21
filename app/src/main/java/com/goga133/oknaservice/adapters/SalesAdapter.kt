package com.goga133.oknaservice.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goga133.oknaservice.R
import com.goga133.oknaservice.models.Office
import com.goga133.oknaservice.models.SaleCard
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.android.synthetic.main.model_office.view.*
import kotlinx.android.synthetic.main.model_sales.view.*
import java.text.SimpleDateFormat
import java.util.*


class SalesAdapter(private val context: Context) : RecyclerView.Adapter<SalesAdapter.SalesHolder>() {

    class SalesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleImage: ImageView = itemView.title_image
        val title: TextView = itemView.title_textView
        val description: TextView = itemView.description_textView

    }

    private var salesArray: Array<SaleCard> = arrayOf<SaleCard> ()

    fun renewItems(newItems: Array<SaleCard>) {
        salesArray = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.model_sales, parent, false)
        return SalesHolder(itemView)
    }

    override fun onBindViewHolder(holder: SalesHolder, position: Int) {
        holder.title.text = salesArray[position].title
        holder.description.text = salesArray[position].description
        // Загрузка Image по URL.
        Glide
            .with(context)
            .load(salesArray[position].imageUrl)
            .circleCrop()
            .into(holder.titleImage)

    }


    override fun getItemCount() = salesArray.size
}
