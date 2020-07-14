package com.goga133.oknaservice.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goga133.oknaservice.R
import com.goga133.oknaservice.models.Product
import com.goga133.oknaservice.ui.lead.LeadViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.model_product.view.*



class ProductsAdapter(
    private val context: Context,
    private val leadViewModel: LeadViewModel
) :
    ListAdapter<Product, ProductsAdapter.ProductHolder>(DiffCallback()) {

    class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.image_product
        val description: TextView = itemView.description_product
        val deleteProductButton: Button = itemView.button_delete_product
    }

    class DiffCallback : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    fun getProductAt(position : Int) : Product{
        return getItem(position)
    }

    fun getElements(): MutableList<Product> {
        return currentList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.model_product, parent, false)
        return ProductHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        Glide.with(context)
            .load(getItem(position).windowIdResource)
            .into(holder.imageView)
        holder.description.text = getItem(position).toString()
        holder.deleteProductButton.setOnClickListener(OnClickRemoveItem(leadViewModel, getItem(position)))
    }

    class OnClickRemoveItem(private val leadViewModel: LeadViewModel, private val item : Product) : View.OnClickListener{
        override fun onClick(v: View?) {
            if (v != null) {
                Snackbar.make(v, "Выбранное окно успешно убрано из корзины!", Snackbar.LENGTH_LONG).show()
            }
            leadViewModel.deleteProduct(item)
        }

    }

}
