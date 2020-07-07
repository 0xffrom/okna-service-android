package com.goga133.oknaservice.ui.lead

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.goga133.oknaservice.R

class LeadFragment : Fragment() {

    private lateinit var leadViewModel: LeadViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        leadViewModel = ViewModelProviders.of(this).get(LeadViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_lead, container, false)
        val textView: TextView = root.findViewById(R.id.text_info)
        leadViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })


        return root
    }
}