package com.recycl.ui.upload

import android.net.Uri
import java.io.File

/**
 * UploadView
 * @author Alexander Peebles
 * Student Number: 150328687
 * Interface to upload item data
 */
interface UploadView {

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
     * displayImagePreview
     * Displays the image preview from the selected gallery photo
     * @param filePath - The device file path where image is located
     */
    fun displayImagePreview(filePath: String)

    /**
     * displayImagePreview
     * Displays the image preview from the camera image taken
     * @param fileUri - The Uri where image is located
     */
    fun displayImagePreview(fileUri: Uri)

    /**
     * showItemNameError
     * Shows error regarding an items name
     */
    fun showItemNameError()

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
     * Displays a toast if the Item could not be uploaded
     */
    fun showSignupError()

    /**
     * onEditItemSuccess
     * Shows a dialog and resets the upload form to upload the next item
     */
    fun onUploadItemSuccess()

}