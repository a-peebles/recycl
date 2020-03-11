package com.recycl.presentation.implementation

import com.recycl.firebase.authentication.FirebaseAuthenticationInterface
import com.recycl.firebase.database.FirebaseDatabaseInterface
import com.recycl.firebase.storage.FirebaseStorageInterface
import com.recycl.presentation.ProfilePresenterInterface
import com.recycl.ui.profile.ProfileView
import javax.inject.Inject


/**
 * ProfilePresenter
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see ProfilePresenterInterface
 * @see ProfileView
 * Displays profile photo, information and statistics for the user
 */
class ProfilePresenter @Inject constructor(private val authenticationInterface: FirebaseAuthenticationInterface,
                                           private val databaseInterface: FirebaseDatabaseInterface,
                                           private val storageInterface: FirebaseStorageInterface) : ProfilePresenterInterface {
    // View for the fragment
    private lateinit var view: ProfileView

    /**
     * getProfile
     * Used to retrieve users profile information
     */
    override fun getProfile() {
        // Gets the profile information
        databaseInterface.getProfileInfo(authenticationInterface.getUserId()) { user ->
            // Displays the users name and description
            view.showName(user.firstName!!, user.lastName!!)
            view.showProfileDescription(user.profileDescription!!)

            // Downloads the users profile photo and displays it
            storageInterface.downloadImage(user.profileImageUri!!){
                view.showProfileImage(it)
            }
            // Displays the total number of items currently for donation and donated
            view.showNumberOfItemsDonated( user.itemsDonated.count())
            view.showNumberOfItemsUploaded( user.itemsUploaded.count())
        }
    }

    /**
     * onOpenReminderTap
     * Button to open ReminderActivity
     */
    override fun onOpenReminderTap() {
        view.openReminder()
    }

    /**
     * setView
     * Sets the view for the Fragment
     * @param view - The view for the Fragment
     */
    override fun setView(view: ProfileView) {
        this.view = view
    }
}
