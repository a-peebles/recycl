package com.recycl.ui.main.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * MainPagerAdapter
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see FragmentManager
 * @see FragmentStatePagerAdapter
 * Adapter used to handle switching between fragments
 */
class MainPagerAdapter(private val manager :FragmentManager) : FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    // Pages to switch between
    private val pages = mutableListOf<Fragment>()

    /**
     * setPages
     * Sets the pages to be displayed in MainActivity
     * @param data The List of Fragments to display
     */
    fun setPages(data: List<Fragment>) {
        pages.clear()
        pages.addAll(data)
        notifyDataSetChanged()
    }

    /**
     * getItem
     * Gets a Fragment to display
     * @param position - Index in list of Fragment to get
     * @return Fragment - Fragment to display
     */
    override fun getItem(position : Int) : Fragment = pages[position]

    /**
     * getCount
     * Gets the number of pages in the list
     * @return Int - list size
     */
    override fun getCount(): Int = pages.size
}