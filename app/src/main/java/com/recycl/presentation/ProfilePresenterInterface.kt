package com.recycl.presentation

import com.recycl.ui.profile.ProfileView


/**
 * ProfilePresenterInterface
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see ProfileView
 * Interface to display profile photo, information and statistics for the user
 */
interface ProfilePresenterInterface: BasePresenterInterface<ProfileView> {


    /**
     * getProfile
     * Used to retrieve users profile information
     */
    fun getProfile()

    /**
     * onOpenReminderTap
     * Button to open ReminderActivity
     */
    fun onOpenReminderTap()

}