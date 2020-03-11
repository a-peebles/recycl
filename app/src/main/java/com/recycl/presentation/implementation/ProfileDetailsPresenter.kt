package com.recycl.presentation.implementation

import android.net.Uri
import com.recycl.common.isProfileDescriptionValid
import com.recycl.firebase.authentication.FirebaseAuthenticationInterface
import com.recycl.firebase.database.FirebaseDatabaseInterface
import com.recycl.firebase.storage.FirebaseStorageInterface
import com.recycl.presentation.ProfileDetailsPresenterInterface
import com.recycl.ui.profiledetails.ProfileDetailsView
import javax.inject.Inject


/**
 * ProfileDetailsPresenter
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see ProfileDetailsPresenterInterface
 * Uploads additional information about the users such as a description and a profile photo
 *
 */
class ProfileDetailsPresenter @Inject constructor(
    private val database: FirebaseDatabaseInterface,
    private val authentication: FirebaseAuthenticationInterface,
    private val storage: FirebaseStorageInterface
): ProfileDetailsPresenterInterface {

    // View for the Activity
    private lateinit var view: ProfileDetailsView
    // Uri for the profile photo on the device
    private lateinit var profileImageUri: Uri
    // Description string
    private var description = ""

    /**
     * setView
     * Sets the view for the presenter to retrieve data from
     * @param view - view for the presenter
     */
    override fun setView(view: ProfileDetailsView) {
        this.view = view
    }

    /**
     * onProfileDescriptionChange
     * Used to set the profile description and validate it
     * @param description - description in the TextView
     */
    override fun onProfileDescriptionChange(description: String) {
        this.description = description
        if(!isProfileDescriptionValid(description)) {
            view.showDescriptionError()
        }
    }

    /**
     * onContinueTap
     * Uploads the image and adds the profile information to Firebase also updates the view
     */
    override fun onContinueTap() {
        storage.uploadImage(profileImageUri) {

            database.addProfileImage(authentication.getUserId(), it)
            database.addProfileDescription(authentication.getUserId(), description)
            view.onDetailsSuccess()
        }

    }

    /**
     * onUploadFromGalleryTap
     * Used to open the gallery as well as verify permissions for photo access if required
     */
    override fun onUploadFromGalleryTap() {
        if (!view.verifyPermissions()) {
            return
        }
        view.chooseGallery()
    }

    /**
     * onCameraTap
     * Used to open the camera to take a photo as well as verify permissions for camera access if
     * required
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
        this.profileImageUri = Uri.parse(filePath)
        view.displayImagePreview(filePath)
    }

    /**
     * showPreview
     * @param uri - uri of image
     * Displays an image in the imageView once taken
     */
    override fun showPreview(uri: Uri) {
        this.profileImageUri = uri
        view.displayImagePreview(uri)
    }
}