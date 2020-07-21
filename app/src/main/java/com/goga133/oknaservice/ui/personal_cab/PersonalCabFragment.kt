package com.goga133.oknaservice.ui.personal_cab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.goga133.oknaservice.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_personal.view.*

class PersonalCabFragment : Fragment() {

    private val mAuth by lazy { FirebaseAuth.getInstance()}
    private val mCurrentUser by lazy { mAuth.currentUser}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // TODO: Не начинал ещё делать.
        val root = inflater.inflate(R.layout.fragment_personal, container, false)

        root.button_logout.setOnClickListener{
            mAuth.signOut()
            findNavController().apply {
                popBackStack(R.id.nav_personal, true);
                navigate(R.id.nav_auth)}
        }
        return root
    }
}