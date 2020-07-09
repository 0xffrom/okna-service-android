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
import com.goga133.oknaservice.objects.Office
import kotlinx.android.synthetic.main.adapter_office.view.*


class OfficesAdapter(private val array_offices: Array<Office>, private val context: Context) :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfficeHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_office, parent, false)
        return OfficeHolder(itemView)
    }

    override fun onBindViewHolder(holder: OfficeHolder, position: Int) {
        holder.addressTextView.text = array_offices[position].Address
        holder.cityTextView.text = array_offices[position].City
        holder.appointmentTextView.text = array_offices[position].Appointment
        holder.mobileTextView.text = array_offices[position].Mobile
        holder.mailTextView.text = array_offices[position].Mail
        holder.workTimeTextView.text = array_offices[position].WorkTime

        holder.addressLayout.setOnClickListener(View.OnClickListener {
            val gmmIntentUri =
                Uri.parse("geo:${array_offices[position].Coordinates}?q=${array_offices[position].City},${(array_offices[position].Address)}")
            Intent(Intent.ACTION_VIEW, gmmIntentUri).let { context.startActivity(it) }
        })
        holder.mailLayout.setOnClickListener(View.OnClickListener {
            val mailIntentUri = Uri.parse("mailto:${array_offices[position].Mail}")
            Intent(Intent.ACTION_VIEW, mailIntentUri).let { context.startActivity(it) }
        })
        holder.telephoneLayout.setOnClickListener(View.OnClickListener {
            // Split делается для того, чтобы выбрать только первый номер:
            val telephoneIntentUri =
                Uri.parse("tel:${array_offices[position].Mobile.split(',')[0]}")
            Intent(Intent.ACTION_VIEW, telephoneIntentUri).let { context.startActivity(it) }
        })
    }


    override fun getItemCount() = array_offices.size
}
