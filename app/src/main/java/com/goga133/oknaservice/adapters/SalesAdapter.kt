package com.goga133.oknaservice.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goga133.oknaservice.R
import com.goga133.oknaservice.models.Office
import com.goga133.oknaservice.models.SaleCard
import kotlinx.android.synthetic.main.model_office.view.*


class SalesAdapter(private val context: Context) : RecyclerView.Adapter<SalesAdapter.SalesHolder>() {

    class SalesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

    private var salesOffices: Array<SaleCard> = arrayOf<SaleCard> ()
    fun renewItems(newItems: Array<SaleCard>) {
        salesOffices = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.model_office, parent, false)
        return SalesHolder(itemView)
    }

    override fun onBindViewHolder(holder: SalesHolder, position: Int) {

    }


    override fun getItemCount() = salesOffices.size
}
