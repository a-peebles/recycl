package com.recycl.presentation.implementation

import com.recycl.firebase.authentication.FirebaseAuthenticationInterface
import com.recycl.presentation.MainPresenterInterface
import com.recycl.ui.main.MainView
import javax.inject.Inject


/**
 * MainPresenter
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see FirebaseAuthenticationInterface
 * Used to process Actionbar clicks
 */
class MainPresenter @Inject constructor(private val authentication: FirebaseAuthenticationInterface)
    : MainPresenterInterface {

    // View for the Activity
    private lateinit var view: MainView


    /**
     * onLogoutTap
     * Used to log the user out and return to the WelcomeActivity
     */
    override fun onLogoutTap() {
        authentication.logOut{view.openWelcome() }

    }

    /**
     * onUploadTap
     * Used to open the UploadActivity
     */
    override fun onUploadTap() {
        view.openUpload()
    }

    /**
     * Sets the view of the Activity
     * @param view - MainView to be parsed to the presenter
     */
    override fun setView(view: MainView) {
        this.view = view
    }


}