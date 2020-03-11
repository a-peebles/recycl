package com.recycl.presentation.implementation

import com.recycl.firebase.authentication.FirebaseAuthenticationInterface
import com.recycl.firebase.database.FirebaseDatabaseInterface
import com.recycl.firebase.storage.FirebaseStorageInterface
import com.recycl.model.Item
import com.recycl.presentation.ViewItemPresenterInterface
import com.recycl.ui.items.view.ViewItemView
import javax.inject.Inject

/**
 * ViewItemPresenter
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see FirebaseDatabaseInterface
 * @see ViewItemPresenterInterface
 * Used to display information and photo of a single item
 */
class ViewItemPresenter @Inject constructor(private val database: FirebaseDatabaseInterface,
                                        private val authentication: FirebaseAuthenticationInterface,
    private val storage: FirebaseStorageInterface): ViewItemPresenterInterface {

    // View for the Activity
    private lateinit var view: ViewItemView

    /**
     * onDonatedButtonTap
     * Used to set an item to donated and update Firebase
     * @param item - The Item to make donated
     */
    override fun onDonatedButtonTap(item: Item) {
        // TODO fix this
        // Changes the donation status
        database.changeItemDonationStatus(item, authentication.getUserId()){ result ->
            when (result) {
                0 -> {
                    view.setDonated()
                }
                1 -> {
                    view.setUndonated()
                }
                else -> {
                    view.setError()

                }
            }
        }
    }

    /**
     * onDeleteButtonTap
     * Used to delete an item when it is no longer needed
     * @param item - The item to delete
     */
    override fun onDeleteButtonTap(item: Item) {
        // Deletes the item information
        database.deleteItem(authentication.getUserId(),item){
            if (it) {
                // Deletes the item image
                storage.deleteImage(item.itemImageUri) { isDeleted ->
                    if (isDeleted) {
                        // Updates the UI
                        view.itemDeleteSuccess()
                    }
                }
            } else {
                // Displays an error
                view.itemDeleteError()
            }
        }
    }

    /**
     * onEditItemButton
     * Sets an item to be edited and parses its image location
     * @param item - Item to be edited
     * @param imageUri - The location of the image in storage
     */
    override fun onEditItemButtonTap(item: Item, imageUri: String) {
        view.openEditItem(item, imageUri)
    }

    /**
     * setView
     * Sets the view of the activity
     * @param view - the view of the Activity
     */
    override fun setView(view: ViewItemView) {
        this.view = view
    }
}