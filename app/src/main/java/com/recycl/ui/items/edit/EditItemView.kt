package com.recycl.ui.items.edit

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.recycl.model.Item
import java.io.File


/**
 * EditItemView
 * @author Alexander Peebles
 * Student Number: 150328687
 * Interface to edit Item data
 */
interface EditItemView {

    /**
     * verifyPermissions
     * Checks the permissions for storage and camera access or requests them
     * @return Boolean value if permission is set
     */
    fun verifyPermissions(): Boolean


    /**
     * getFilePath
     * Gets the file Path for the image
     * @return File - Corresponding file of the image
     */
    fun getFilePath(): File

    /**
     * startCamera
     * Opens device camera to take an image
     * @param file - File to save image capture to
     */
    fun startCamera(file: File)

    /**
     * chooseGallery
     * Opens device photo gallery to upload an image
     */
    fun chooseGallery()

    /**
     * Displays the image preview from the selected gallery photo
     * @param filePath - The device file path where image is located
     */
    fun displayImagePreview(filePath: String)

    /**
     * Displays the image preview from the camera image taken
     * @param fileUri - The Uri where image is located
     */
    fun displayImagePreview(fileUri: Uri)

    /**
     * showItemNameError
     * Shows error regarding an items name
     */
    fun showItemNameError()

    /**
     * showItemLocationError
     * Shows error regarding an item location
     */
    fun showItemCategoryError()

    /**
     * showItemDescriptionError
     * Shows error regarding an item description
     */
    fun showItemDescriptionError()

    /**
     * showItemLocationError
     * Shows error regarding an item location
     */
    fun showItemLocationError()

    /**
     * newFile
     * Creates a new file to store the captured image from the camera
     * @return File - File to return
     */
    fun newFile(): File?

    /**
     * showEditError
     * Displays a toast if the Item could not be edited
     */
    fun showEditError()

    /**
     * onEditItemSuccess
     * Shows a dialog and opens the Item in ItemActivity to view
     * @param item - Item to edit
     */
    fun onEditItemSuccess(item: Item)

    /**
     * onEditItemSuccess
     * Shows a dialog and opens the Item in ItemActivity to view with new image
     * @param item - Item to edit
     * @param imageUri - Item image
     */
    fun onEditItemSuccess(item: Item, imageUri: String)

    /**
     * onEditItemError
     * Shows a dialog if the Item response from server is unsuccessful
     */
    fun onEditItemError()
}