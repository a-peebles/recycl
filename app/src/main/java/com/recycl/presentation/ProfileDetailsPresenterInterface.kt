package com.recycl.presentation

import android.net.Uri
import com.recycl.ui.profiledetails.ProfileDetailsView

/**
 * ProfileDetailsPresenterInterface
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see ProfileDetailsView
 * Interface for the ProfileDetailsPresenter for user to add additional information
 */
interface ProfileDetailsPresenterInterface: BasePresenterInterface<ProfileDetailsView> {

    /**
     * onContinueTap
     * Uploads the image and adds the profile information to Firebase also updates the view
     */
    fun onContinueTap()

    /**
     * onUploadFromGalleryTap
     * Used to open the gallery as well as verify permissions for photo access if required
     */
    fun onUploadFromGalleryTap()

    /**
     * onCameraTap
     * Used to open the camera to take a photo as well as verify permissions for camera access if
     * required
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
     * onProfileDescriptionChange
     * Used to set the profile description and validate it
     * @param description - description in the TextView
     */
    fun onProfileDescriptionChange(description: String)
}