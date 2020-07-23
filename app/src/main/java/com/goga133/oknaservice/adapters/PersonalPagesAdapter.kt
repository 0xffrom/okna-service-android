package com.goga133.oknaservice.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.goga133.oknaservice.ui.personal_cab.PersonalInfoFragment
import com.goga133.oknaservice.ui.personal_cab.PersonalOrdersFragment

class PersonalPagesAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    public val fragments = arrayOf(Page(PersonalInfoFragment(), "Моя информация"),
        Page(PersonalOrdersFragment(),"Заказы"))

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        // Тут можно что-то делать с ними.

        return fragments[position].fragment
    }

    data class Page(val fragment: Fragment, val title : String)
}