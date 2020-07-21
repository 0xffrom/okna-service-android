package com.goga133.oknaservice.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.goga133.oknaservice.R
import com.goga133.oknaservice.adapters.OfficesAdapter
import com.goga133.oknaservice.adapters.SalesAdapter
import kotlinx.android.synthetic.main.fragment_contacts.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val viewManager = LinearLayoutManager(root.context)
        val salesAdapter = SalesAdapter(root.context)

        root.sales_recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = salesAdapter
        }

        homeViewModel.arraySales.observe(viewLifecycleOwner,
            Observer { salesAdapter.renewItems(it)})


        return root
    }
}