package com.recycl.presentation

import com.recycl.ui.login.LoginView


/**
 * LoginPresenterInterface
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see BasePresenterInterface
 * @see LoginView
 * Interface used to validate user login input, respond to button clicks and
 * call Firebase Authentication functions
 *
 */
interface LoginPresenterInterface : BasePresenterInterface<LoginView> {


    /**
     * onLoginTap
     * Validates the login request, checks credentials and updates the UI
     */
    fun onLoginTap()


    /**
     * onEmailChanged
     * Used to set the users email and validates it
     * @param email - email text in the EditText field
     */
    fun onEmailChanged(email: String)

    /**
     * onPasswordChanged
     * Used to set the users password and validates it
     * @param password - password text in the EditText field
     */
    fun onPasswordChanged(password: String)


}