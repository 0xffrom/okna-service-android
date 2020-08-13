package com.goga133.oknaservice.ui.home

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.goga133.oknaservice.R
import com.goga133.oknaservice.adapters.SalesAdapter
import com.goga133.oknaservice.core.NetworkApi
import com.goga133.oknaservice.core.NetworkService
import com.goga133.oknaservice.core.Status
import com.goga133.oknaservice.models.News
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var salesAdapter : SalesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val viewManager = LinearLayoutManager(root.context)
        salesAdapter = SalesAdapter(root.context)

        root.sales_recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = salesAdapter
        }
        observeGetPosts()
        homeViewModel.getNews()
        return root
    }

    private fun observeGetPosts() {
        homeViewModel.newsLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> viewOneLoading()
                Status.SUCCESS -> viewOneSuccess(it.data)
                Status.ERROR -> viewOneError(it.error)
            }
        })
    }

    private fun viewOneLoading() {
        progress_homeView.visibility = View.VISIBLE
        Log.d(TAG, "Loading")
    }

    private fun viewOneSuccess(data: List<News>?) {
        progress_homeView.visibility = View.INVISIBLE
        salesAdapter.renewItems(data!!)
        Log.d(TAG, "Success")
    }

    private fun viewOneError(error: Throwable?) {
        Log.d(TAG, "Error")

        Snackbar.make(this.requireView(), when(error) {
            is java.net.SocketTimeoutException -> "Проверьте подключение к интернету!"
            else -> "Ошибка загрузки данных!"
        }, Snackbar.LENGTH_LONG).show()
        homeViewModel.getNews()
    }


}