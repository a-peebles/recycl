package com.recycl.ui.main


/**
 * MainView
 * @author Alexander Peebles
 * Student Number: 150328687
 * Interface for the main Activity used to respond to events
 */
interface MainView {

    /**
     * openWelcome
     * Opens the WelcomeActivity if the user is not logged in
     */
    fun openWelcome()

    /**
     * openUpload
     * Opens the UploadActivity if the button is clicked
     */
    fun openUpload()
}