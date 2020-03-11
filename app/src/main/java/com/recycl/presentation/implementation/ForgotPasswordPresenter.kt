package com.recycl.presentation.implementation

import com.recycl.common.isEmailValid
import com.recycl.firebase.authentication.FirebaseAuthenticationInterface
import com.recycl.presentation.ForgotPasswordPresenterInterface
import com.recycl.ui.forgotpassword.ForgotPasswordView
import javax.inject.Inject


/**
 * ForgotPasswordPresenter
 * @author Alexander Peebles
 * Student Number: 150328687
 * Used to respond to user input and call Firebase operations for
 * ForgetPasswordActivity
 * @see FirebaseAuthenticationInterface
 */
class ForgotPasswordPresenter @Inject constructor(
    private val authentication: FirebaseAuthenticationInterface) : ForgotPasswordPresenterInterface {

    // View for the Activity
    private lateinit var view: ForgotPasswordView
    // String to hold the user email
    private lateinit var userEmail: String


    /**
     * Sets the view for the Activity
     * @param view  - View for ForgetPasswordActivity
     */
    override fun setView(view: ForgotPasswordView) {
        this.view = view

    }

    /**
     * onEmailChanged
     * Used to set the users email and validates it
     * @param email - email text in the EditText field
     */
    override fun onEmailChanged(email: String) {
        userEmail = email
        if(!isEmailValid(email)) {
            view.showEmailError()
        }
    }

    /**
     * onForgetPasswordClick
     * Calls the Firebase authentication function to send a password
     * reset email and updates the UI once sent
     */
    override fun onForgotPasswordClick() = authentication.sendPasswordResetEmail(userEmail) {
        isSuccessful -> view.onEmailSent(isSuccessful, userEmail)
    }



}