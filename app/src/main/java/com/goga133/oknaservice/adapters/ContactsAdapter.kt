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
import com.goga133.oknaservice.models.Contact
import kotlinx.android.synthetic.main.model_office.view.*


class ContactsAdapter(private val context: Context) :
    RecyclerView.Adapter<ContactsAdapter.ContactHolder>() {

    class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    private var arrayContacts: List<Contact> = listOf<Contact> ()
    fun renewItems(newItems: List<Contact>?) {
        arrayContacts = newItems ?: listOf()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.model_office, parent, false)
        return ContactHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.addressTextView.text = arrayContacts[position].address
        holder.cityTextView.text = arrayContacts[position].city
        holder.appointmentTextView.text = arrayContacts[position].office
        holder.mobileTextView.text = arrayContacts[position].phone
        holder.mailTextView.text = arrayContacts[position].email
        holder.workTimeTextView.text = arrayContacts[position].workTime

        holder.addressLayout.setOnClickListener(View.OnClickListener {
            val gmmIntentUri =
                Uri.parse("geo:${arrayContacts[position].mapCoordinates}?q=${arrayContacts[position].city},${(arrayContacts[position].address)}")
            Intent(Intent.ACTION_VIEW, gmmIntentUri).let { context.startActivity(it) }
        })
        holder.mailLayout.setOnClickListener(View.OnClickListener {
            val mailIntentUri = Uri.parse("mailto:${arrayContacts[position].email}")
            Intent(Intent.ACTION_VIEW, mailIntentUri).let { context.startActivity(it) }
        })
        holder.telephoneLayout.setOnClickListener(View.OnClickListener {
            // Split делается для того, чтобы выбрать только первый номер:
            val telephoneIntentUri =
                Uri.parse("tel:${arrayContacts[position].phone.split(',')[0]}")
            Intent(Intent.ACTION_VIEW, telephoneIntentUri).let { context.startActivity(it) }
        })
    }


    override fun getItemCount() = arrayContacts.size
}
