package com.recycl.ui.forgotpassword



/**
 * ForgotPasswordActivity
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see ForgotPasswordActivity
 * View used to reset a user password using their email
 */
interface ForgotPasswordView {

    /**
     * showEmailError
     * Displays an error symbol on the text box due to invalid email format
     *
     */
    fun showEmailError()

    /**
     * onEmailSent
     *  Notifies user that email has been sent successfully and
     *  loads the LoginActivity for them to login
     *  @param isSuccessful - Result of attempting to send a password reset email
     *  @param email - the users email
     */
    fun onEmailSent(isSuccessful: Boolean, email: String)


}