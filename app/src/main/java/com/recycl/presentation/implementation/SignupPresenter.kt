package com.recycl.presentation.implementation

import android.util.Log
import com.recycl.common.arePasswordsSame
import com.recycl.common.isEmailValid
import com.recycl.common.isNameValid
import com.recycl.common.isPasswordValid
import com.recycl.firebase.authentication.FirebaseAuthenticationInterface
import com.recycl.firebase.database.FirebaseDatabaseInterface
import com.recycl.model.SignupRequest
import com.recycl.presentation.SignupPresenterInterface
import com.recycl.ui.signup.SignupView
import javax.inject.Inject


/**
 * SignupPresenter
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see FirebaseAuthenticationInterface
 * @see FirebaseDatabaseInterface
 * @see SignupPresenterInterface
 * Used to validate user input for signup and create/authenticate new users
 */
class SignupPresenter @Inject constructor(
    private val database: FirebaseDatabaseInterface,
    private val authentication: FirebaseAuthenticationInterface) : SignupPresenterInterface {


    private val TAG ="SignupPresenter"
    // View for the activity
    private lateinit var view: SignupView
    // Data to create a new user
    private val userData = SignupRequest()

    /**
     * setView
     * Sets the view for the activity
     * @param view - View for the Activity
     */
    override fun setView(view: SignupView) {
        this.view = view
    }

    /**
     * onFirstNameChanged
     * Used to set the users first name and validate it
     * @param firstName -  name text in the EditText field
     */
    override fun onFirstNameChanged(firstName: String) {
        userData.firstName = firstName
        if(!isNameValid(firstName)) {
             view.showFirstNameError()
        }
    }

    /**
     * onLastNameChanged
     * Used to set the users last name and validate it
     * @param lastName -  name text in the EditText field
     */
    override fun onLastNameChanged(lastName: String) {
        userData.lastName = lastName
        if(!isNameValid(lastName)) {
            view.showLastNameError()
        }
    }

    /**
     * onEmailChanged
     * Used to set the users email and validate it
     * @param email - email text in the EditText field
     */
    override fun onEmailChanged(email: String) {
        userData.email = email
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
        userData.password =  password
        if (!isPasswordValid(password)) {
            view.showPasswordError()
        }
    }

    /**
     * onRepeatPasswordChanged
     * Used to set the users password again and validate it
     * @param repeatPassword - password text in the EditText field
     */
    override fun onRepeatPasswordChanged(repeatPassword: String) {
        userData.repeatPassword = repeatPassword
        if (!arePasswordsSame(userData.password, repeatPassword)) {
            view.showPasswordMatchingError()
        }
    }


    /**
     * onSignupTap
     * Used to validate all the user input and create a user on Firebase
     */
    override fun onSignupTap() {
        if (userData.isValid()) {
            Log.i(TAG, "Valid user data")
            // Assigns the signup info to variables
            val (firstName, lastName, email, password) = userData

            // Calls the signup function and returns the result
            authentication.signup(firstName, lastName, email, password) {
                isSuccessful -> onSignupResult(isSuccessful, firstName, lastName, email)
            }
        }
        // If there is an error validating the data
        else {
            view.showSignupError()
        }
    }

    /**
     * onSignupResult
     * Used to create a user in the database send a verification email and let the user in to the app
     * or display an error
     * @param isSuccessful - Result of the signup function
     * @param firstName - Users first name
     * @param lastName - Users last name
     * @param email - Users email
     */
    private fun onSignupResult(isSuccessful: Boolean, firstName: String,
                                 lastName: String, email: String) {
        // If authentication successful add their information to the database and send v
        // Verification email
        if (isSuccessful) {
            createUser(firstName, lastName, email)
            verifyEmail()
            view.onSignupSuccess()
        } else {
            view.showSignupError()
        }
    }

    /**
     * createUser
     * Creates a user on the Firebase database
     * @param firstName - Users first name
     * @param lastName - Users last name
     * @param email - Users email
     */
    private fun createUser(firstName: String, lastName: String, email: String) {
        val id = authentication.getUserId()
        database.createUser(id, firstName, lastName, email)
    }

    /**
     * verifyEmail
     * Sends a verification email to the users inbox
     */
    private fun verifyEmail() {
        authentication.verifyEmail {
            isSuccessful -> view.onVerificationEmailSent(isSuccessful, userData.email)
        }
    }
}