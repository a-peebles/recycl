package com.recycl.presentation.implementation

import com.recycl.common.isEmailValid
import com.recycl.common.isPasswordValid
import com.recycl.firebase.authentication.FirebaseAuthenticationInterface
import com.recycl.model.LoginRequest
import com.recycl.presentation.LoginPresenterInterface
import com.recycl.ui.login.LoginView
import javax.inject.Inject

/**
 * LoginPresenter
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see FirebaseAuthenticationInterface
 * Used to validate user login input, respond to button clicks and
 * call Firebase Authentication functions
 *
 */
class LoginPresenter @Inject constructor(
    private val authentication: FirebaseAuthenticationInterface) : LoginPresenterInterface {

    // View for Activity
    private lateinit var view: LoginView

    // LoginRequest in model
    private val loginRequest = LoginRequest()

    /**
     * Sets the view for the Activity
     * @param view -  The corresponding view for the activity
     */
    override fun setView(view: LoginView) {
        this.view = view

    }

    /**
     * onLoginTap
     * Validates the login request, checks credentials and updates the UI
     */
    override fun onLoginTap() {
        if (loginRequest.isValid()) {
            authentication.login(loginRequest.email, loginRequest.password) { isSuccess ->
                if (isSuccess) {
                    view.onLoginSuccess()
                } else {
                    view.showLoginError()
                }

            }
        }
    }



    /**
     * onEmailChanged
     * Used to set the users email and validate it
     * @param email - email text in the EditText field
     */
    override fun onEmailChanged(email: String) {
        loginRequest.email = email
        if (!isEmailValid(email)) {
            view.showEmailError()
        }
    }

    /**
     * onPasswordChanged
     * Used to set the users password and validate it
     * @param password - password text in the EditText field
     */
    override fun onPasswordChanged(password: String) {
        loginRequest.password = password
        if(!isPasswordValid(password)) {
            view.showPasswordError()
        }
    }

}