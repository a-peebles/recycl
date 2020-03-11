package com.recycl.presentation

import com.recycl.model.Item
import com.recycl.ui.items.view.ViewItemView

/**
 * ViewItemPresenter
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see ViewItemView
 * Interface for displaying information and photo of a single item
 */
interface ViewItemPresenterInterface:BasePresenterInterface<ViewItemView> {

    /**
     * onDonatedButtonTap
     * Used to set an item to donated and update Firebase
     * @param item - The Item to make donated
     */
    fun onDonatedButtonTap(item: Item)

    /**
     * onEditItemButton
     * Sets an item to be edited and parses its image location
     * @param item - Item to be edited
     * @param imageUri - The location of the image in storage
     */
    fun onEditItemButtonTap(item: Item, imageUri: String)

    /**
     * onDeleteButtonTap
     * Used to delete an item when it is no longer needed
     * @param item - The item to delete
     */
    fun onDeleteButtonTap(item: Item)
}