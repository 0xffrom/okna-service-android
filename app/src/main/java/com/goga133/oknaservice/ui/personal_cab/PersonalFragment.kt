package com.goga133.oknaservice.ui.personal_cab

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.goga133.oknaservice.R
import com.goga133.oknaservice.adapters.PersonalPagesAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_personal.*
import kotlinx.android.synthetic.main.fragment_personal.view.*

class PersonalFragment : Fragment() {

    private val mAuth by lazy { FirebaseAuth.getInstance() }
    private val mCurrentUser by lazy { mAuth.currentUser }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_personal, container, false)
    }

    override fun onStart() {
        super.onStart()
        // Если пользователь неавторизован:
        if (mCurrentUser == null)
            toLoginFragment()
    }

    private fun logOut(){
        mAuth.signOut()
        toLoginFragment()
    }

    private fun toLoginFragment() {
        if (isVisible) {
            findNavController().apply {
                popBackStack(R.id.nav_personal, true);
                navigate(R.id.nav_auth)
            }
        }
    }

    // ======== Toolbar + ViewPager ======== //
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        // ===== ViewPager with TabLayout ===== //
        val personalPagesAdapter = PersonalPagesAdapter(this)
        val viewPager = section_viewPager.apply {
            adapter = personalPagesAdapter
        }

        val tabLayout = section_tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = personalPagesAdapter.fragments[position].title
        }.attach()
        // ===== ViewPager with TabLayout ===== //
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar_personal, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_logout -> {
                logOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    // ======== Toolbar + ViewPager ======== //


}