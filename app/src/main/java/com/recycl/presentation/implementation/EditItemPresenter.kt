package com.recycl.presentation.implementation

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.recycl.common.isItemCategoryValid
import com.recycl.common.isItemDescriptionValid
import com.recycl.common.isItemLocationValid
import com.recycl.common.isItemNameValid
import com.recycl.firebase.authentication.FirebaseAuthenticationInterface
import com.recycl.firebase.database.FirebaseDatabaseInterface
import com.recycl.firebase.storage.FirebaseStorageInterface
import com.recycl.model.Item
import com.recycl.presentation.EditItemPresenterInterface
import com.recycl.ui.items.edit.EditItemView


/**
 * EditItemPresenter
 * @author Alexander Peebles
 * Student Number: 150328687
 *
 * Used to perform validation and Firebase calls when items are edited
 * @see FirebaseDatabaseInterface
 * @see FirebaseDatabaseInterface
 * @see FirebaseStorageInterface
 *
 *
 */
class EditItemPresenter @javax.inject.Inject constructor(private val database: FirebaseDatabaseInterface,
                                                         private val authentication: FirebaseAuthenticationInterface,
                                                         private val storage: FirebaseStorageInterface): EditItemPresenterInterface, AdapterView.OnItemSelectedListener {

    // View for the Activity
    private lateinit var view: EditItemView

    // Item variables
    private lateinit var itemImageUri: Uri
    private lateinit var itemId: String
    private var itemName = ""
    private var itemLocation = ""
    private var itemDescription = ""
    private var itemCategory = ""
    private var imageChanged = false
    private var itemImageFirebasePath = ""
    private val TAG = "EditItemPresenter"
    private lateinit var item: Item

    /**
     * setView
     * @param view View class for the Activity
     */
    override fun setView(view: EditItemView) {
        this.view = view
    }

    /**
     * setItemData
     * Used to assign the variables for editing
     * @param item  - Item to edit
     */
    override fun setItemData(item: Item) {
        this.itemId = item.itemId
        this.itemName = item.itemName
        this.itemCategory = item.itemCategory
        this.itemLocation = item.itemLocation
        this.itemDescription = item.itemDescription
        this.itemImageFirebasePath = item.itemImageUri

    }

    /**
     * onItemNameChanged
     * Used to set the new item Name and validate it
     * @param itemName - text in itemName corresponding EditText
     */
    override fun onItemNameChanged(itemName: String) {
        this.itemName = itemName
        if (!isItemNameValid(itemName)) {
            view.showItemNameError()
        }
    }

    /**
     * onUploadFromGalleryTap
     * Opens system photo gallery to retrieve images for an item
     * also verifies permissions
     */
    override fun onUploadFromGalleryTap() {
        if (!view.verifyPermissions()) {
            return
        }
        view.chooseGallery()
    }

    /**
     * onCameraTap
     * Opens system camera to take a photo for an item
     * also verifies permissions
     */
    override fun onCameraTap() {
        if (!view.verifyPermissions()) {
            return
        }
        val file = view.newFile()

        view.startCamera(file!!)
    }


    /**
     * showPreview
     * @param filePath - system path of image
     * Displays an image in the imageView once selected
     */
    override fun showPreview(filePath: String) {
        this.itemImageUri = Uri.parse(filePath)
        view.displayImagePreview(filePath)
        imageChanged = true
    }

    /**
     * showPreview
     * @param uri - uri of image
     * Displays an image in the imageView once taken
     */
    override fun showPreview(uri: Uri) {
        this.itemImageUri = uri
        view.displayImagePreview(uri)
        imageChanged = true

    }

    /**
     * onItemDescriptionChanged
     * Used to set the new item Description and validate it
     * @param itemDescription - text in itemDescription corresponding EditText
     */
    override fun onItemDescriptionChanged(itemDescription: String) {
        this.itemDescription = itemDescription
        if (!isItemDescriptionValid(itemDescription)) {
            view.showItemDescriptionError()
        }
    }

    /**
     * onItemLocationChanged
     * Used to set the new item Location and validate it
     * @param itemLocation - text in itemLocation corresponding EditText
     */
    override fun onItemLocationChanged(itemLocation: String) {
        this.itemLocation = itemLocation
        if (!isItemLocationValid(itemLocation)) {
            view.showItemLocationError()
        }
    }

    /**
     * onEditItemButtonTap
     * Used to validate input one final time and then update the item with new credentials
     */
    override fun onEditItemButtonTap() {
        if (isItemNameValid(itemName)
            && isItemCategoryValid(itemCategory)
            && isItemDescriptionValid(itemDescription)
            && isItemLocationValid(itemLocation)) {
            // Uploads the image if new
            if(imageChanged) {
                storage.uploadImage(itemImageUri) { s ->

                    // Creates a new item with the new Firebase image URL
                    item = Item(
                        itemId, itemName, itemCategory, itemLocation,
                        itemDescription, s, false
                    )
                }
            } else {
                    // Creates a new item with the original Firebase image URL
                    item = Item(
                        itemId, itemName, itemCategory, itemLocation,
                        itemDescription, itemImageFirebasePath, false
                    )
            }
                // Calls edit item
            database.editItem(authentication.getUserId(),item) {
                // Passes result to function
                onEditItemResult(it)
            }
        }
        else {
            // Display error if parameters invalid
            view.showEditError()
        }
    }

    /**
     * onEditItemResult
     * Calls view function to update display if successful
     * @param isSuccessful - result of editing an item
     */
    private fun onEditItemResult(isSuccessful: Boolean) {
        if (isSuccessful) {
            if (imageChanged) {
                storage.downloadImage(item.itemImageUri){
                    view.onEditItemSuccess(item, it)
                }
            }
            else {
                view.onEditItemSuccess(item)
            }
        } else {
            view.onEditItemError()
        }

    }

    /**
     * onNothingSelected
     * Used if no value is set on the itemCategory
     */
    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    /**
     * onItemSelected
     * Used to set the itemCategory based of the user input for dropdown menu
     * @param parent - The adapter for the Spinner
     * @param spinnerView - The view for the Spinner
     * @param position -  The item selected in the Spinner
     * @param id -  the id of the Spinner
     */
    override fun onItemSelected(parent: AdapterView<*>?, spinnerView: View?, position: Int, id: Long) {
        val itemCategory = parent?.getItemAtPosition(position).toString()
        this.itemCategory = itemCategory
        if (!isItemCategoryValid(itemCategory)) {
            view.showItemCategoryError()
        }
    }
}