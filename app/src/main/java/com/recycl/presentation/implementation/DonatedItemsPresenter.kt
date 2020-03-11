package com.recycl.presentation.implementation

import com.recycl.firebase.authentication.FirebaseAuthenticationInterface
import com.recycl.firebase.database.FirebaseDatabaseInterface
import com.recycl.firebase.storage.FirebaseStorageInterface
import com.recycl.model.Item
import com.recycl.presentation.DonatedItemsPresenterInterface
import com.recycl.ui.items.donated.DonatedItemsView
import javax.inject.Inject


/**
 *  DonatedItemsPresenter
 *  @author Alexander Peebles
 *  Student Number: 150328687
 *  @see DonatedItemsPresenterInterface
 * Presenter implementation for the item feed Fragment of donated items
 * @param  authenticationInterface - Used for Firebase Authentication tasks
 * @param databaseInterface - Used for Firebase Database tasks
 * @param storageInterface - Used for Firebase Storage tasks
 */
class DonatedItemsPresenter @Inject constructor(private val authenticationInterface: FirebaseAuthenticationInterface,
                                                private val databaseInterface: FirebaseDatabaseInterface,
                                                private val storageInterface: FirebaseStorageInterface
): DonatedItemsPresenterInterface {


    // View for the fragment
    private lateinit var view: DonatedItemsView


    /**
     * setView
     * @param view View class for the Fragment
     */
    override fun setView(view: DonatedItemsView) {
        this.view = view
    }

    /**
     * Gets all the donated items from the database to display
     */
    override fun getDonatedItems() {
        // Listens for changes on the database and sets the donation status of the item
        databaseInterface.listenForDonatedItems( authenticationInterface.getUserId()) { item ->
            item.isDonated = true
            // Downloads the corresponding image for the item and adds both the item and the image
            // To the display
            storageInterface.downloadImage(item.itemImageUri){bitmap ->
                view.addItem(item, bitmap)
            }
        }
    }


    /**
     * onItemTap
     * @param item - Item to display
     * @param imageUri - The file path of the image stored on the device
     */
    override fun onItemTap(item: Item, imageUri: String) {
        view.viewItem(item, imageUri)

    }


}