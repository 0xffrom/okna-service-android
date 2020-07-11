package com.goga133.oknaservice.ui.lead

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goga133.oknaservice.R
import com.goga133.oknaservice.adapters.ProductsAdapter
import com.goga133.oknaservice.models.Product
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_lead.view.*

class LeadFragment : Fragment() {

    private lateinit var leadViewModel: LeadViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_lead, container, false)
        leadViewModel = ViewModelProviders.of(this).get(LeadViewModel::class.java)

        root.list_products.layoutManager = LinearLayoutManager(root.context)
        root.list_products.setHasFixedSize(false)
        val adapter = ProductsAdapter(root.context, leadViewModel)

        leadViewModel.getProducts().observe(viewLifecycleOwner,
            Observer<List<Product>> { t -> adapter.submitList(t) })

        root.list_products.adapter = adapter

        // Удаление по свайпу:
        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
            ) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    Snackbar.make(root, "Выбранное окно успешно убрано из корзины!", Snackbar.LENGTH_LONG).show()
                    leadViewModel.deleteProduct(adapter.getProductAt(viewHolder.adapterPosition))
                }
            }).attachToRecyclerView(root.list_products)

        return root
    }
}