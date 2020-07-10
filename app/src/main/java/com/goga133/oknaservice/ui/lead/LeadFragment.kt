package com.goga133.oknaservice.ui.lead

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.goga133.oknaservice.R
import com.goga133.oknaservice.adapters.OfficesAdapter
import com.goga133.oknaservice.adapters.ProductsAdapter
import com.goga133.oknaservice.models.Product
import com.goga133.oknaservice.models.ProductDatabase
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_contacts.view.*
import kotlinx.android.synthetic.main.fragment_lead.view.*

class LeadFragment : Fragment() {

    private lateinit var leadViewModel: LeadViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        leadViewModel = ViewModelProviders.of(this).get(LeadViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_lead, container, false)
        val viewManager = LinearLayoutManager(root.context)

        root.list_products.apply {

            setHasFixedSize(true)

            layoutManager = viewManager

            leadViewModel.getProducts().observe(viewLifecycleOwner, Observer { adapter = ProductsAdapter(
                it as ArrayList<Product>, context, leadViewModel)})

        }


        return root
    }
}