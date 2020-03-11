package com.recycl.presentation

import com.recycl.model.Item
import com.recycl.ui.items.all.ItemsView


/**
 * AllItemsPresenter
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see AllItemsPresenter
 * Interface for the item feed Fragment
 *
 */
interface AllItemsPresenterInterface: BasePresenterInterface<ItemsView> {


    /**
     * getAllItems
     * Gets all the items listed on the database and adds them to the application item feed
     */
    fun getItemsToDonate()

    /**
     * onItemTap
     * Used to load the item into ItemActivity
     * @param item - The item to view
     * @param imageUri - The items corresponding locally stored image
     */
    fun onItemTap(item: Item, imageUri: String)
}