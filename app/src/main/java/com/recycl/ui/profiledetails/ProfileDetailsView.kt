package com.recycl.ui.profiledetails

import android.net.Uri
import java.io.File

/**
 * ProfileDetailsView
 * @author Alexander Peebles
 * Student Number: 150328687
 * Interface for the user to upload additional information
 */
interface ProfileDetailsView {

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
     * showDescriptionError
     * Shows error regarding a profile description
     */
    fun showDescriptionError()

    /**
     * showImageError
     * Shows error regarding image upload
     */
    fun showImageError()

    /**
     * onDetailsSuccess
     * Used to perform action if details were uploaded successfully
     */
    fun onDetailsSuccess()

    /**
     * onImageError
     * Used to show a toast if an image has not been uploaded.
     */
    fun onImageError()

    /**
     * newFile
     * Creates a new file to store the captured image from the camera
     * @return File - File to return
     */
    fun newFile(): File?
}