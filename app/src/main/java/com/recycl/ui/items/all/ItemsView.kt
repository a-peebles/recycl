package com.recycl.ui.items.all

import com.recycl.model.Item

/**
 * ItemsView
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see ItemsFragment
 * Interface to display Item cards in a feed to scroll through
 */
interface ItemsView {

    /**
     * showNoDataDescription
     * Shows the noItem TextField if there are no items
     */
    fun showNoDataDescription()

    /**
     * hideNoDataDescription
     * Removes the noItem TextField
     */
    fun hideNoDataDescription()

    /**
     * addItem
     * Used to add an Item to an Adapter
     * @param item - The Item to add to the Fragment
     * @param imageUri - The corresponding Item image
     */
    fun addItem(item: Item, imageUri: String)

    /**
     * viewItem
     * Used to view an Item in a seperate Activity
     * @param item - The item to view
     * @param imageUri - The items corresponding image
     */
    fun viewItem(item: Item, imageUri: String)


}