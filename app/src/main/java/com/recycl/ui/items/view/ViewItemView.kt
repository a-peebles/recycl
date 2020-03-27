package com.recycl.ui.items.view

import com.recycl.model.Item

/**
 * ViewItemView
 * @author Alexander Peebles
 * Student Number: 150328687
 *
 * Interface used to display item information, image and options
 */
interface ViewItemView {

    /**
     * setDonated
     * Used to set the item to a donated item
     */
    fun setDonated()

    /**
     * setUndonated
     * Used to set the item as an item that is to be donated
     */
    fun setUndonated()

    /**
     * setError
     * Used to display an error if the item donation status could not be changed
     */
    fun setError()

    /**
     * itemDeleteSuccess
     * Displays a toast if the item was able to be deleted successfully
     */
    fun itemDeleteSuccess()

    /**
     * itemDeleteError
     * Displays a toast if the item could not be deleted
     */
    fun itemDeleteError()

    /**
     * openEditItem
     * @param item - Item to be edited
     * @param imageUri - Corresponding Item image locations
     */
    fun openEditItem(item: Item, imageUri: String)
}