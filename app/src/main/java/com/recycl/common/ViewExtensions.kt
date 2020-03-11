package com.recycl.common

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.viewpager.widget.ViewPager
import org.w3c.dom.Text

/**
 * ViewExtensions
 * @author Alex Peebles
 * Student Number: 150328687
 *
 * Inline functions used to adapt view elements for Model View Presenter (MVP) model
 *
 */

/**
 * View.onClick
 * Overrides the onclick method to parse the click handler, onClickHandler are implemented
 * in Presenters and are  set in Activities and Fragments
 * @see View
 * @param onClickHandler - Clicks are handled in the corresponding Presenter class
 *
 */
inline fun View.onClick(crossinline onClickHandler: () -> Unit) {
    setOnClickListener{ onClickHandler() }
}


/**
 * onTextChanged
 * Overwritten to allow errors to be shown to the user
 * in real time when they input text into fields
 * @see EditText
 * @param onTextChangedHandler
 */
inline fun EditText.onTextChanged(crossinline onTextChangedHandler: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {

        // Notifies that the edit text has been changed
        override fun afterTextChanged(s: Editable?) = Unit

        // Notifies that the characters in the CharSequence are about to be replaced
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int,
                                       after: Int) = Unit

        // Notifies that the text has now been changed parses onTextChangeHandler the new string
        override fun onTextChanged(input: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChangedHandler(input?.toString() ?: "")
        }
    })
}





/**
 * onPageChange
 * Used to establish when the user clicks a bottom navigation menu button and changes the displayed
 * Fragment
 * @see ViewPager
 * @see Fragment
 * @param onPageChangeHandler the Handler used to change pages
 * TODO Flesh out comments here
 */
inline fun ViewPager.onPageChange(crossinline onPageChangeHandler: (Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

        override fun onPageScrollStateChanged(state: Int) = Unit

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

        override fun onPageSelected(position: Int) = onPageChangeHandler(position)

    })
}