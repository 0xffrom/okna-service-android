package com.goga133.oknaservice.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goga133.oknaservice.R
import com.goga133.oknaservice.models.Product
import com.goga133.oknaservice.ui.lead.LeadViewModel
import kotlinx.android.synthetic.main.adapter_product.view.*
import kotlinx.coroutines.launch


class ProductsAdapter(
    private var array_products: ArrayList<Product>,
    private val context: Context,
    private val leadViewModel: LeadViewModel
) :
    RecyclerView.Adapter<ProductsAdapter.ProductHolder>() {

    class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.image_product
        val description: TextView = itemView.description_product
        val deleteProductButton: Button = itemView.button_delete_product
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_product, parent, false)
        return ProductHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        Glide.with(context)
            .load(array_products[position].windowIdResource)
            .into(holder.imageView)
        holder.description.text = array_products[position].toString()
        holder.deleteProductButton.setOnClickListener(deleteProduct(position))
    }

    private fun deleteProduct(position: Int): View.OnClickListener? = View.OnClickListener {
        leadViewModel.viewModelScope.launch {
            leadViewModel.deleteProduct(array_products[position]).also {
                array_products.remove(array_products[position])
                notifyItemRemoved(position)
            }
        }
    }

    override fun getItemCount() = array_products.size
}
