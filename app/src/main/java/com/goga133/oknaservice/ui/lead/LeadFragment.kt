package com.goga133.oknaservice.ui.lead

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goga133.oknaservice.R
import com.goga133.oknaservice.adapters.ProductsAdapter
import com.goga133.oknaservice.models.Calculator
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
        root.main_info_layout.visibility = View.INVISIBLE

        leadViewModel = ViewModelProviders.of(this).get(LeadViewModel::class.java)

        root.list_products.layoutManager = LinearLayoutManager(root.context)
        root.list_products.setHasFixedSize(false)
        val adapter = ProductsAdapter(root.context, leadViewModel)

        leadViewModel.getProducts().observe(viewLifecycleOwner,
            Observer<List<Product>> { t ->
                adapter.submitList(t)
                // Проверка на количество элементов:
                // Если элементов 0 => Корзина пуста.
                if (t != null && t.isEmpty()){
                    root.cart_null_textView.visibility = View.VISIBLE
                    root.main_info_layout.visibility = View.INVISIBLE
                } else{
                    root.cart_null_textView.visibility = View.INVISIBLE
                    root.main_info_layout.visibility = View.VISIBLE
                    root.isDelivery_textView.text = when(getProductsDelivery(t)){
                        true -> "Доставка: Требуется."
                        false -> "Доставка: Не требуется."
                    }
                    // TODO: Android 5.1 -> при ItemTouch не изменяется цена, Android 10.0 -> наоборот 0_о

                    root.sum_textView.text = "Итоговая сумма к оплате: ${getProductsSum(t)} рублей."
                }
            })

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

    private fun getProductsDelivery(products : List<Product>) : Boolean{
        for(i in products.indices){
            if(products[i].isWinDelivery)
                return true
        }
        return false
    }

    private fun getProductsSum(products : List<Product>) : Int{
        var sum = 0
        for(i in products.indices)
            sum += products[i].priceSum

        // Если есть доставка, то плюсуем только одну цену за доставку:
        if (getProductsDelivery(products))
            sum += Calculator.Price().delivery
        return sum
    }
}