package com.recycl.presentation

import com.recycl.model.Item
import com.recycl.ui.items.donated.DonatedItemsView


/**
 *  DonatedItemsPresenterInterface
 *  @author Alexander Peebles
 *  Student Number: 150328687
 *  @see BasePresenterInterface
 *  @see DonatedItemsView
 * Presenter interface for the item feed Fragment of donated items
 */
interface DonatedItemsPresenterInterface: BasePresenterInterface<DonatedItemsView> {


    /**
     * Gets all the donated items from the database to display
     */
    fun getDonatedItems()

    /**
     * onItemTap
     * @param item - Item to display
     * @param imageUri - The file path of the image stored on the device
     */
    fun onItemTap(item: Item, imageUri: String)


}