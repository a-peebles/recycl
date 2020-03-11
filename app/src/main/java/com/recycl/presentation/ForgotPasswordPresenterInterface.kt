package com.recycl.presentation

import com.recycl.ui.forgotpassword.ForgotPasswordView


/**
 * ForgotPasswordPresenterInterace
 * @author Alexander Peebles
 * Student Number: 150328687
 * Interface to respond to user input and call Firebase operations for
 * ForgetPasswordActivity
 * @see BasePresenterInterface
 * @see ForgotPasswordView
 */
interface ForgotPasswordPresenterInterface: BasePresenterInterface<ForgotPasswordView> {

    /**
     * onEmailChanged
     * Used to set the users email and validates it
     * @param email - email text in the EditText field
     */
    fun onEmailChanged(email: String)

    /**
     * onForgetPasswordClick
     * Calls the Firebase authentication function to send a password
     * reset email and updates the UI once sent
     */
    fun onForgotPasswordClick()
}