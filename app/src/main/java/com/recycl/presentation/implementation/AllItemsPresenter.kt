package com.recycl.presentation.implementation

import com.recycl.firebase.authentication.FirebaseAuthenticationInterface
import com.recycl.firebase.database.FirebaseDatabaseInterface
import com.recycl.firebase.storage.FirebaseStorageInterface
import com.recycl.model.Item
import com.recycl.presentation.AllItemsPresenterInterface
import com.recycl.ui.items.all.ItemsView
import javax.inject.Inject


/**
 * AllItemsPresenter
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see AllItemsPresenterInterface
 * Presenter implementation for the item feed Fragment of undonated items
 * @param  authenticationInterface - Used for Firebase Authentication tasks
 * @param databaseInterface - Used for Firebase Database tasks
 * @param storageInterface - Used for Firebase Storage tasks
 *
 */
class AllItemsPresenter @Inject constructor(
    private val authenticationInterface: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface,
    private val storageInterface: FirebaseStorageInterface): AllItemsPresenterInterface {


    private val TAG = "AllItemsPresenter"

    // View for the Fragment
    private lateinit var view: ItemsView


    /**
     * setView
     * @param view View class for the Fragment
     */
    override fun setView(view: ItemsView) {
        this.view = view
    }


    /**
     * getItemsToDonate
     * Gets all the items listed on the database and adds them to the application item feed
     */
    override fun getItemsToDonate() {
        databaseInterface.listenForItemsToDonate( authenticationInterface.getUserId()) { item ->
            storageInterface.downloadImage(item.itemImageUri){bitmap ->
                view.addItem(item, bitmap)
            }
        }
    }

    /**
     * onItemTap
     * Used to load the item into ItemActivity
     * @param item - The item to view
     * @param imageUri - The items corresponding locally stored image
     */
    override fun onItemTap(item: Item, imageUri: String) {
        view.viewItem(item, imageUri)
    }

}