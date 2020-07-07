package com.goga133.oknaservice.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goga133.oknaservice.R
import com.goga133.oknaservice.adapters.OfficesAdapter
import com.goga133.oknaservice.objects.Office
import kotlinx.android.synthetic.main.fragment_contacts.*

class ContactsFragment : Fragment() {

    private lateinit var contactsViewModel: ContactsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contactsViewModel = ViewModelProviders.of(this).get(ContactsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_contacts, container, false)
        val viewManager = LinearLayoutManager(root.context)

        root.findViewById<RecyclerView>(R.id.list_offices).apply {

            setHasFixedSize(true)

            layoutManager = viewManager

            contactsViewModel.arrayOffices.observe(viewLifecycleOwner,
                Observer { adapter = OfficesAdapter(it, context) })
        }

        return root
    }
}