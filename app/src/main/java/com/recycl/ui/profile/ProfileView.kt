package com.recycl.ui.profile


/**
 * ProfileView
 * @author Alexander Peebles
 * Student Number: 150328687
 * Interface to display the users profile information
 */
interface ProfileView {

    /**
     * showName
     * Used to show a users name on the profileName TextView
     * @param firstName - Users first name
     * @param lastName - Users last name
     */
    fun showName(firstName: String, lastName: String)

    /**
     * showProfileDescription
     * Show the users profile description in the profileDescription TextView
     * @param description - User description to display
     */
    fun showProfileDescription(description: String)

    /**
     * showProfileImage
     * Used to show the users profile image
     */
    fun showProfileImage(photoPath: String)

    /**
     * openReminder
     * Opens the reminder activity
     */
    fun openReminder()

    /**
     * showNumberOfItemsUploaded
     * Shows the number of items uploaded by the user
     * @param itemCount - The number of items the user has uploaded
     */
    fun showNumberOfItemsUploaded(itemCount: Int)

    /**
     * showNumberOfItemsDonated
     * Shows the number of items donated by the user
     * @param donatedItemCount - The number of items the user has donated
     */
    fun showNumberOfItemsDonated(donatedItemCount: Int)
}