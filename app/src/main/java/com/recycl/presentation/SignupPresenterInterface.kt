package com.recycl.presentation

import com.recycl.ui.signup.SignupView

/**
 * SignupPresenterInterface
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see SignupPresenterInterface
 * Interface for validating user input for signup and create/authenticate new users
 */
interface SignupPresenterInterface : BasePresenterInterface<SignupView> {

    /**
     * onFirstNameChanged
     * Used to set the users first name and validate it
     * @param firstName -  name text in the EditText field
     */
    fun onFirstNameChanged(firstName: String)

    /**
     * onLastNameChanged
     * Used to set the users last name and validate it
     * @param lastName -  name text in the EditText field
     */
    fun onLastNameChanged(lastName: String)

    /**
     * onEmailChanged
     * Used to set the users email and validate it
     * @param email - email text in the EditText field
     */
    fun onEmailChanged(email: String)

    /**
     * onPasswordChanged
     * Used to set the users password and validate it
     * @param password - password text in the EditText field
     */
    fun onPasswordChanged(password: String)

    /**
     * onRepeatPasswordChanged
     * Used to set the users password again and validate it
     * @param repeatPassword - password text in the EditText field
     */
    fun onRepeatPasswordChanged(repeatPassword: String)

    /**
     * onSignupTap
     * Used to validate all the user input and create a user on Firebase
     */
    fun onSignupTap()



}