package com.recycl.ui.signup



/**
 * SignupView
 * @author Alexander Peebles
 * Student Number: 150328687
 * Interface used to display login form validation and information related to login requests
 */
interface SignupView {

    /**
     * onSignupSuccess
     * Actions to perform if the signup was successful is successful
     */
    fun onSignupSuccess()

    /**
     * showSignupError
     * Show a signup error if the user could not be signed up successfully
     */
    fun showSignupError()

    /**
     * showPasswordError
     * Shows an error if the password is invalid
     */
    fun showPasswordError()

    /**
     * showPasswordError
     * Shows an error if the repeated password is invalid
     */
    fun showRepeatPasswordError()

    /**
     * showEmailError
     * Shows an error if the users email is invalid
     */
    fun showEmailError()

    /**
     * showFirstNameError
     * Shows an error if the user first name is invalid
     */
    fun showFirstNameError()

    /**
     * showLastNameError
     * Shows an error if the user last name is invalid
     */
    fun showLastNameError()

    /**
     * showPasswordMatchingError
     * Displays a message if the users password and repeat password don't match
     */
    fun showPasswordMatchingError()

    /**
     * onVerificationEmailSent
     * sends a verification email
     * to the user if the signup is successful
     * @param isSuccessful - If the email send  is successful
     * @param email - The email to be sent to
     */
    fun onVerificationEmailSent(isSuccessful: Boolean, email: String)


}