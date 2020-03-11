package com.recycl.model

import com.recycl.common.arePasswordsSame
import com.recycl.common.isEmailValid
import com.recycl.common.isNameValid



/**
 * SignupRequest
 * @author Alex Peebles
 * Student Number: 150328687
 * Stores data needed to make a signup a new user on Firebase
 * @param firstName - Users given first name: String
 * @param lastName - Users given last name: String
 * @param email - users email: String
 * @param password  -  Users password: String
 * @param repeatPassword - Users password reentered: String
 */
data class SignupRequest(var firstName: String = "",
                            var lastName: String = "",
                            var email: String = "",
                            var password: String = "",
                            var repeatPassword: String = ""
                            ) {

    /**
     * isValid
     * Checks that all credentials are valid
     * @return True if all credentials are input correctly
     */
    fun isValid() : Boolean = isNameValid(firstName) && isNameValid(lastName) &&
    isEmailValid(email)  && arePasswordsSame(password, repeatPassword)

}


