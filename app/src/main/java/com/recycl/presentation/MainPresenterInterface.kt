package com.recycl.presentation

import com.recycl.ui.main.MainView


/**
 * MainPresenterInterface
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see BasePresenterInterface
 * @see MainView
 * Interface to process Actionbar clicks
 */
interface MainPresenterInterface: BasePresenterInterface<MainView> {


    /**
     * onLogoutTap
     * Used to log the user out and return to the WelcomeActivity
     */
    fun onLogoutTap()

    /**
     * onUploadTap
     * Used to open the UploadActivity
     */
    fun onUploadTap()
}