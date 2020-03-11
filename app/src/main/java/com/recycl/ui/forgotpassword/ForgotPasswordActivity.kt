package com.recycl.ui.forgotpassword

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.recycl.R
import com.recycl.common.onClick
import com.recycl.common.onTextChanged
import com.recycl.common.showToast
import com.recycl.forgotPasswordPresenter
import com.recycl.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_forgot_password.*

/**
 * ForgotPasswordActivity
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see AppCompatActivity
 * @see ForgotPasswordView
 * Activity used to reset a user password using their email
 */
class ForgotPasswordActivity : AppCompatActivity(), ForgotPasswordView {


    // Presenter class for the activity
    private val presenter by lazy { forgotPasswordPresenter() }
    private val TAG = "ForgotPasswordActivity"


    /**
     * onCreate
     * Sets up the Activity its user interface
     * and the presenter view
     * @param savedInstanceState - Activity's previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        presenter.setView(this)
        initUi()
    }

    /**
     * initUi
     * Sets up the interactive components of the user interface
     */
    private fun initUi() {

        forgotPasswordEmail.onTextChanged { presenter.onEmailChanged(it) }
        forgotPasswordButton.onClick{ presenter.onForgotPasswordClick() }
    }

    /**
     *  onEmailSent
     *  Notifies user that email has been sent successfully and
     *  loads the LoginActivity for them to login
     *  @param isSuccessful - Result of attempting to send a password reset email
     *  @param email - the users email
     */
    override fun onEmailSent(isSuccessful: Boolean, email: String) {
        if (isSuccessful) {
            showToast(this, getString(R.string.password_reset_email_success,email))
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            showToast(this, getString(R.string.password_reset_email_error))

        }
    }

    /**
     * showEmailError
     * Displays an error symbol on the text box due to invalid email format
     *
     */
    override fun showEmailError() {
        forgotPasswordEmail.error =  getString(R.string.email_error)
    }

}
