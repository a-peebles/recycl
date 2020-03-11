package com.recycl.firebase.authentication


/**
 * FirebaseAuthenticationInterface
 * @author Alex Peebles
 * Student Number: 150328687
 * This interface is used to authenticate users logging in and signing up,
 * using Firebase to create an account.
 * Can also retrieve user credentials
 */
interface FirebaseAuthenticationInterface {


    /**
     * login
     * Used to log the user in and allow access to the application
     * @param email - Users assigned email
     * @param password - Users set password
     * @param onResult - The result of the login request from Firebase
     */
    fun login(email: String, password: String, onResult: (Boolean) -> Unit)


    /**
     * signup
     * Allows the user to create an account to use the application
     * @param firstName - Users first name
     * @param lastName - Users surname
     * @param email - Users designated email address for logging in
     * @param password - Users set password
     * @param onResult - The result of the signup request from Firebase
     */
    fun signup(firstName: String, lastName : String, email: String, password: String, onResult: (Boolean) -> Unit)

    /**
     * getUserId
     * Getter for the users unique id
     * @return Users Id in String format
     */
    fun getUserId(): String

    /**
     * getEmail
     * Getter for the users email
     * @return Users email in string format
     */
    fun getEmail(): String

    /**
     * getName
     * Getter for the users first and last name
     * @return Concatenated string of the users name
     */
    fun getName(): String

    /**
     * logOut
     * Logs the user out of the application
     * @param onResult -  Result of the logout request from Firebase
     */
    fun logOut(onResult: () -> Unit)

    /**
     * verifyEmail
     * Sends a verification email to the user if signup is successful
     * @param onResult - Result of the email verification request from Firebase
     */
    fun verifyEmail(onResult: (Boolean) -> Unit)

    /**
     * sendPasswordResetEmail
     * Sends a password reset email to the email input by the user
     * @param email - Email user input
     * @param onResult - Result of the password reset request from Firebase
     */
    fun sendPasswordResetEmail(email: String, onResult: (Boolean) -> Unit)
}