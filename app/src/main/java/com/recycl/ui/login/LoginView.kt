package com.recycl.ui.login


/**
 * LoginView
 * @author Alexander Peebles
 * Student Number: 150328687
 * Interface used to display information relating to login requests
 */
interface LoginView {

    /**
     * showPasswordError
     * Shows an error if the password is incorrect
     */
    fun showPasswordError()

    /**
     * showEmailError
     * Shows an error if the email is not formatted correctly
     */
    fun showEmailError()

    /**
     * onLoginSuccess
     * Actions to perform if the login is successful
     */
    fun onLoginSuccess()

    /**
     * showLoginError
     * Displays an error to the user
     */
    fun showLoginError()

}