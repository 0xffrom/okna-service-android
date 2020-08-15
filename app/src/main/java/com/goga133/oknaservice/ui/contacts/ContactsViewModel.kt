package com.goga133.oknaservice.ui.contacts

import androidx.lifecycle.MutableLiveData
import com.goga133.oknaservice.core.BaseViewModel
import com.goga133.oknaservice.models.Event
import com.goga133.oknaservice.models.Contact

class ContactsViewModel : BaseViewModel() {

    val contactsLiveData = MutableLiveData<Event<List<Contact>>>()

    fun getContacts() {
        requestWithLiveData(contactsLiveData) {
            api.getContacts()
        }
    }
}