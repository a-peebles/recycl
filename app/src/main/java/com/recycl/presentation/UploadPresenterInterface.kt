package com.recycl.presentation

import android.net.Uri
import com.recycl.ui.upload.UploadView


/**
 * UploadPresenterInterface
 * @author Alexander Peebles
 * Student Number: 150328687
 * Interface to validate data for Items and upload to Firebase
 */
interface UploadPresenterInterface: BasePresenterInterface<UploadView> {

    /**
     * onItemNameChanged
     * Used to set the new item Name and validate it
     * @param itemName - text in itemName corresponding EditText
     */
    fun onItemNameChanged(itemName: String)

    /**
     * onItemDescriptionChanged
     * Used to set the new item Description and validate it
     * @param itemDescription - text in itemDescription corresponding EditText
     */
    fun onItemDescriptionChanged(itemDescription: String)

    /**
     * onItemLocationChanged
     * Used to set the new item Location and validate it
     * @param itemLocation - text in itemLocation corresponding EditText
     */
    fun onItemLocationChanged(itemLocation: String)

    /**
     * onUploadFromGalleryTap
     * Opens system photo gallery to retrieve images for an item
     * also verifies permissions
     */
    fun onUploadFromGalleryTap()

    /**
     * onCameraTap
     * Opens system camera to take a photo for an item
     * also verifies permissions
     */
    fun onCameraTap()

    /**
     * showPreview
     * @param filePath - system path of image
     * Displays an image in the imageView once selected
     */
    fun showPreview(filePath: String)

    /**
     * showPreview
     * @param uri - uri of image
     * Displays an image in the imageView once taken
     */
    fun showPreview(uri: Uri)

    /**
     * onUploadItemButtonTap
     * Used to validate input one final time and then upload the item to Firebase
     */
    fun onUploadItemButtonTap()

}