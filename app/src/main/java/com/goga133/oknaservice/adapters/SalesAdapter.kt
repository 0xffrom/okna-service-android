package com.goga133.oknaservice.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goga133.oknaservice.R
import com.goga133.oknaservice.core.CircleTransform
import com.goga133.oknaservice.models.SaleCard
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.model_sales.view.*


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
        Picasso
            .get()
            .load(salesArray[position].imageUrl)
            .transform(CircleTransform())
            .into(holder.titleImage)

    }


    override fun getItemCount() = salesArray.size
}
