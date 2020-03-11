package com.recycl.ui.signup

interface SignupView {


    fun onSignupSuccess()

    fun showSignupError()

    fun showPasswordError()

    fun showRepeatPasswordError()

    fun showEmailError()

    fun showFirstNameError()

    fun showLastNameError()

    fun showPasswordMatchingError()

    fun onVerificationEmailSent(isSuccessful: Boolean, email: String)


}