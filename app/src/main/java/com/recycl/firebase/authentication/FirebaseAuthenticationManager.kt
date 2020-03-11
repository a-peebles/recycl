package com.recycl.firebase.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

/**
 *
 * FirebaseAuthenticationManager
 * @author Alex Peebles
 * Student Number: 150328687
 * @see FirebaseAuthenticationInterface
 * @see FirebaseAuth
 * This class is used to authenticate users logging in and signing up,
 * using Firebase to create an account.
 * Can also retrieve user credentials
 */
class FirebaseAuthenticationManager @Inject constructor(
    private val authentication: FirebaseAuth): FirebaseAuthenticationInterface {

     /**
      * login
      * Used to log the user in and allow access to the application
      * @param email - Users assigned email
      * @param password - Users set password
      * @param onResult - The result of the login request from Firebase
      */
     override fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
         // Calls the firebase method to sign-in wih the users credentials
        authentication.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            // If request complete and successful onResult set to true
            onResult(it.isComplete && it.isSuccessful)
        }
     }


     /**
      * signup
      * Allows the user to create an account to use the application
      * @param firstName - Users first name
      * @param lastName - Users surname
      * @param email - Users designated email address for logging in
      * @param password - Users set password
      * @param onResult - The result of the signup request from Firebase
      */
     override fun signup(firstName: String, lastName: String, email: String, password: String,
    onResult: (Boolean) -> Unit    ) {
        // Creates a new user on Firebase using user credentials  and adds a listener
        authentication.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            // If request okay create a user profile and set onResult to true
            if (it.isComplete && it.isSuccessful) {
                authentication.currentUser?.updateProfile(
                    UserProfileChangeRequest
                    .Builder()
                    .setDisplayName("$firstName $lastName")
                    .build())
                onResult(true)
            } else {
                // If there is some sort of error set onResult to false
                onResult(false)
            }
        }
    }

     /**
      * getUserId
      * Getter for the users unique id
      * @return Users Id in String format
      */
     override fun getUserId(): String = authentication.currentUser?.uid ?: ""

     /**
      * getEmail
      * Getter for the users email
      * @return Users email in string format
      */
     override fun getEmail(): String = authentication.currentUser?.email ?: ""

     /**
      * getName
      * Getter for the users first and last name
      * @return Concatenated string of the users name
      */
     override fun getName(): String = authentication.currentUser?.displayName ?: ""


     /**
      * logOut
      * Logs the user out of the application
      * @param onResult -  Result of the logout request from Firebase
      */
     override fun logOut(onResult: () -> Unit) {
        authentication.signOut()
         onResult()
     }

     /**
      * verifyEmail
      * Sends a verification email to the user if signup is successful
      * @param onResult - Result of the email verification request from Firebase
      */
     override fun verifyEmail(onResult: (Boolean) -> Unit) {
         authentication.currentUser?.sendEmailVerification()?.addOnCompleteListener { task ->
             if (task.isSuccessful) {
                 onResult(true)
             } else {
                 onResult(false)
             }
         }
     }

     /**
      * sendPasswordResetEmail
      * Sends a password reset email to the email input by the user
      * @param email - Email user input
      * @param onResult - Result of the password reset request from Firebase
      */
     override fun sendPasswordResetEmail(email: String, onResult: (Boolean) -> Unit) {

         authentication.sendPasswordResetEmail(email)
             .addOnCompleteListener{task ->
                 if (task.isSuccessful) {
                     onResult(true)
                 } else {
                     onResult(false)
                 }
             }
     }
 }