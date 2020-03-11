package com.recycl.presentation.implementation

import com.recycl.firebase.authentication.FirebaseAuthenticationInterface
import com.recycl.presentation.WelcomePresenterInterface
import com.recycl.ui.welcome.WelcomeView
import javax.inject.Inject

/**
 * WelcomePresenter
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see FirebaseAuthenticationInterface
 * @see WelcomePresenterInterface
 * Used to display buttons to log the user in or signup unless already authenticated
 */
class WelcomePresenter @Inject constructor(
    private val authenticationInterface: FirebaseAuthenticationInterface
) : WelcomePresenterInterface {

    // View for the Activity
    private lateinit var view: WelcomeView


    /**
     * Sets the View for the Activity
     * @param view  - View for the Activity
     */
    override fun setView(view: WelcomeView) {
        this.view = view
    }

    /**
     * viewReady
     * Checks if the user is logged in and sends them straight to the MainActivity
     */
    override fun viewReady() {
        if(authenticationInterface.getUserId().isNotBlank()) {
            view.startMainScreen()
        }
    }
}