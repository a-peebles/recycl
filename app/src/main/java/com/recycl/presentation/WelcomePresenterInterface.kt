package com.recycl.presentation

import com.recycl.ui.welcome.WelcomeView


/**
 * WelcomePresenterInterface
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see WelcomeView
 * Interface used to display buttons to log the user in or signup unless already authenticated
 */
interface WelcomePresenterInterface: BasePresenterInterface<WelcomeView> {

    /**
     * viewReady
     * Checks if the user is logged in and sends them straight to the MainActivity
     */
    fun viewReady()

}