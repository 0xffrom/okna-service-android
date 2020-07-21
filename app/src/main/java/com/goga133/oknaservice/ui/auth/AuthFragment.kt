package com.goga133.oknaservice.ui.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.goga133.oknaservice.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_auth.view.*

class AuthFragment : Fragment() {

    private val mAuth by lazy { FirebaseAuth.getInstance()}
    private val mCurrentUser by lazy { mAuth.currentUser}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_auth, container, false)
        val authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        root.button_continue.setOnClickListener {
            root.findNavController().navigate(R.id.nav_auth_input_phone)
        }
        return root
    }

    override fun onStart() {
        super.onStart()
        // Если пользователь авторизован, отправляем его в ЛК:
        if(mCurrentUser != null){
            findNavController().apply {
                popBackStack(R.id.nav_auth, true);
                navigate(R.id.nav_personal)}
        }

    }
}



