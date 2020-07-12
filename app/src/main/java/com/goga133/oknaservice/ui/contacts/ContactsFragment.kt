package com.goga133.oknaservice.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goga133.oknaservice.R
import com.goga133.oknaservice.adapters.OfficesAdapter
import kotlinx.android.synthetic.main.fragment_contacts.view.*

class ContactsFragment : Fragment() {

    private lateinit var contactsViewModel: ContactsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contactsViewModel = ViewModelProviders.of(this).get(ContactsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_contacts, container, false)

        val viewManager = LinearLayoutManager(root.context)
        val officesAdapter = OfficesAdapter(root.context)

        root.list_offices.apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = officesAdapter
        }

        contactsViewModel.arrayOffices.observe(viewLifecycleOwner,
            Observer { officesAdapter.renewItems(it)})

        return root
    }
}