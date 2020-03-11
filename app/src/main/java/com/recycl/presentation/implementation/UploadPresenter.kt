package com.recycl.presentation.implementation

import android.net.Uri
import android.view.View
import android.widget.AdapterView
import com.recycl.common.*
import com.recycl.firebase.authentication.FirebaseAuthenticationInterface
import com.recycl.firebase.database.FirebaseDatabaseInterface
import com.recycl.firebase.storage.FirebaseStorageInterface
import com.recycl.model.Item
import com.recycl.presentation.UploadPresenterInterface
import com.recycl.presentation.ViewItemPresenterInterface
import com.recycl.ui.upload.UploadView
import javax.inject.Inject


/**
 * UploadPresenter
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see FirebaseDatabaseInterface
 * @see ViewItemPresenterInterface
 * Used to validate data for Items and upload to Firebase
 */
class UploadPresenter @Inject constructor( private val database: FirebaseDatabaseInterface,
                                           private val authentication: FirebaseAuthenticationInterface,
                                           private val storage: FirebaseStorageInterface): UploadPresenterInterface, AdapterView.OnItemSelectedListener {

    // View for the Activity
    private lateinit var view: UploadView
    private val TAG = "UploadPresenter"

    // Item data to be uploaded
    private lateinit var itemImageUri: Uri
    private var itemName = ""
    private var itemLocation = ""
    private var itemDescription = ""
    private var itemCategory = ""


    /**
     * setView
     * @param view View class for the Activity
     */
    override fun setView(view: UploadView) {
        this.view = view
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
    }

    /**
     * showPreview
     * @param uri - uri of image
     * Displays an image in the imageView once taken
     */
    override fun showPreview(uri: Uri) {
        this.itemImageUri = uri
        view.displayImagePreview(uri)
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
     * onUploadItemButtonTap
     * Used to validate input one final time and then upload the item to Firebase
     */
    override fun onUploadItemButtonTap() {
        if (isItemNameValid(itemName)
            && isItemCategoryValid(itemCategory)
            && isItemDescriptionValid(itemDescription)
            && isItemLocationValid(itemLocation)) {

            // Uploads the image
            storage.uploadImage(itemImageUri) { s ->

                // Creates a new item with a blank id
                val item = Item( "", itemName, itemCategory, itemLocation,
                    itemDescription, s, false)

                // Adds item to Firebase
                database.addItem(authentication.getUserId(),item) {
                    // Passes the result to function
                    onUploadItemResult(it)
                }
            }
        }
        else {
            // Display error if parameters invalid
            view.showSignupError()

        }

    }
    /**
     * onUploadItemResult
     * Calls view function to update display if successful
     * @param isSuccessful - result of uploading an item
     */
    private fun onUploadItemResult(isSuccessful: Boolean) {
        if (isSuccessful) {
            view.onUploadItemSuccess()
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