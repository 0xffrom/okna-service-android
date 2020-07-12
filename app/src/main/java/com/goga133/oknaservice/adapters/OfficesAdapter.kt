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
import kotlinx.android.synthetic.main.adapter_office.view.*


class OfficesAdapter(private val context: Context) :
    RecyclerView.Adapter<OfficesAdapter.OfficeHolder>() {

    class OfficeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityTextView: TextView = itemView.text_view_city
        val appointmentTextView: TextView = itemView.text_view_appointment
        val addressTextView: TextView = itemView.text_view_address
        val mobileTextView: TextView = itemView.text_view_telephone
        val mailTextView: TextView = itemView.text_view_mail
        val workTimeTextView: TextView = itemView.text_view_time
        val addressLayout: LinearLayout = itemView.layout_address
        val mailLayout: LinearLayout = itemView.layout_mail
        val telephoneLayout: LinearLayout = itemView.layout_telephone
    }

    private var arrayOffices: Array<Office> = arrayOf<Office> ()
    fun renewItems(newItems: Array<Office>) {
        arrayOffices = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfficeHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_office, parent, false)
        return OfficeHolder(itemView)
    }

    override fun onBindViewHolder(holder: OfficeHolder, position: Int) {
        holder.addressTextView.text = arrayOffices[position].Address
        holder.cityTextView.text = arrayOffices[position].City
        holder.appointmentTextView.text = arrayOffices[position].Appointment
        holder.mobileTextView.text = arrayOffices[position].Mobile
        holder.mailTextView.text = arrayOffices[position].Mail
        holder.workTimeTextView.text = arrayOffices[position].WorkTime

        holder.addressLayout.setOnClickListener(View.OnClickListener {
            val gmmIntentUri =
                Uri.parse("geo:${arrayOffices[position].Coordinates}?q=${arrayOffices[position].City},${(arrayOffices[position].Address)}")
            Intent(Intent.ACTION_VIEW, gmmIntentUri).let { context.startActivity(it) }
        })
        holder.mailLayout.setOnClickListener(View.OnClickListener {
            val mailIntentUri = Uri.parse("mailto:${arrayOffices[position].Mail}")
            Intent(Intent.ACTION_VIEW, mailIntentUri).let { context.startActivity(it) }
        })
        holder.telephoneLayout.setOnClickListener(View.OnClickListener {
            // Split делается для того, чтобы выбрать только первый номер:
            val telephoneIntentUri =
                Uri.parse("tel:${arrayOffices[position].Mobile.split(',')[0]}")
            Intent(Intent.ACTION_VIEW, telephoneIntentUri).let { context.startActivity(it) }
        })
    }


    override fun getItemCount() = arrayOffices.size
}
