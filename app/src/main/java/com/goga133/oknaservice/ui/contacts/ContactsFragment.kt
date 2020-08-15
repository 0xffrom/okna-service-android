package com.goga133.oknaservice.ui.contacts

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.goga133.oknaservice.R
import com.goga133.oknaservice.adapters.ContactsAdapter
import com.goga133.oknaservice.models.Status
import com.goga133.oknaservice.models.Contact
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.fragment_contacts.view.*

class ContactsFragment : Fragment() {

    private lateinit var officesAdapter: ContactsAdapter
    private lateinit var contactsViewModel : ContactsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_contacts, container, false)

        contactsViewModel = ViewModelProviders.of(this).get(ContactsViewModel::class.java)

        val viewManager = LinearLayoutManager(root.context)
        officesAdapter = ContactsAdapter(root.context)

        root.list_offices.apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = officesAdapter
        }

        contactsViewModel.contactsLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> viewOneLoading()
                Status.SUCCESS -> viewOneSuccess(it.data)
                Status.ERROR -> viewOneError(it.error)
            }
        })

        observeGetPosts()
        contactsViewModel.getContacts()

        return root
    }

    private fun observeGetPosts() {
        contactsViewModel.contactsLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> viewOneLoading()
                Status.SUCCESS -> viewOneSuccess(it.data)
                Status.ERROR -> viewOneError(it.error)
            }
        })
    }

    private fun viewOneLoading() {
        if (isVisible)
            progress_contactsView.visibility = View.VISIBLE
        Log.d(ContentValues.TAG, "Loading")
    }

    private fun viewOneSuccess(data: List<Contact>?) {
        if (isVisible)
            progress_contactsView.visibility = View.INVISIBLE
        officesAdapter.renewItems(data!!)
        Log.d(ContentValues.TAG, "Success")
    }

    private fun viewOneError(error: Throwable?) {
        Log.d(ContentValues.TAG, "Error")

        if (isVisible) {
            Toast.makeText(
                context, when (error) {
                    is java.net.SocketTimeoutException -> "Проверьте подключение к интернету!"
                    else -> "Ошибка загрузки данных!"
                }, Toast.LENGTH_LONG
            ).show()
        }
        contactsViewModel.getContacts()
    }
}