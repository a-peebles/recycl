package com.recycl.ui.main.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.recycl.ui.main.MainActivity


class MainPagerAdapter(private val manager :FragmentManager) : FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = mutableListOf<Fragment>()

    fun setPages(data: List<Fragment>) {
        pages.clear()
        pages.addAll(data)
        notifyDataSetChanged()
    }


    override fun getItem(position : Int) : Fragment = pages[position]


    override fun getCount(): Int = pages.size
}