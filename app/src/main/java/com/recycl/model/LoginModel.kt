package com.recycl.model

import com.recycl.common.isEmailValid
import com.recycl.common.isPasswordValid

/**
 * LoginRequest
 * @author Alex Peebles
 * Student Number: 150328687
 * Stores data needed to make a login request on Firebase
 * @param email - users saved email: String
 * @param password  -  Users set password: String
 */
data class LoginRequest(var email: String = "",
                   var password: String = "") {

    /**
     * isValid
     * Checks that both the email and password entered are of correct length
     * @return true if both email and password are of correct length
     */
    fun isValid() = isEmailValid(email) && isPasswordValid(password)
}