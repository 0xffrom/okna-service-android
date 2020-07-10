package com.goga133.oknaservice.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goga133.oknaservice.R
import com.goga133.oknaservice.models.Product
import kotlinx.android.synthetic.main.adapter_product.view.*


class ProductsAdapter(private val array_products: List<Product>, private val context: Context) :
    RecyclerView.Adapter<ProductsAdapter.ProductHolder>() {

    class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView : ImageView = itemView.image_product
        val description : TextView = itemView.description_product
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_product, parent, false)
        return ProductHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(array_products[position].windowIdResource)
            .into(holder.imageView)
        holder.description.text = array_products[position].toString()
    }


    override fun getItemCount() = array_products.size
}
