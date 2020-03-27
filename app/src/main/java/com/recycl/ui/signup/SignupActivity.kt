package com.recycl.ui.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.recycl.R
import com.recycl.common.Constants
import com.recycl.common.onClick
import com.recycl.common.onTextChanged
import com.recycl.common.showToast
import com.recycl.signupPresenter
import com.recycl.ui.login.LoginActivity
import com.recycl.ui.profiledetails.ProfileDetailsActivity
import kotlinx.android.synthetic.main.activity_signup.*


/**
 * SignupActivity
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see AppCompatActivity
 * @see SignupView
 * Activity used to display login form and information related to login requests
 */
class SignupActivity : AppCompatActivity(), SignupView {

    // Presenter used to handle button clicks
    private val presenter by lazy { signupPresenter() }


    /**
     * onCreate
     * Sets up the Activity user interface
     * and the presenter view
     * @param savedInstanceState - Activity's previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        presenter.setView(this)
        initUi()


    }

    /**
     * initUi
     * Sets up the interactive components of the user interface
     */
    private fun initUi() {
        signupFirstName.onTextChanged { presenter.onFirstNameChanged(it) }
        signupLastName.onTextChanged { presenter.onLastNameChanged(it) }
        signupEmail.onTextChanged { presenter.onEmailChanged(it) }
        signupPassword.onTextChanged { presenter.onPasswordChanged(it) }
        signupPasswordRepeat.onTextChanged { presenter.onRepeatPasswordChanged(it) }
        signupButton.onClick { presenter.onSignupTap() }
        loginRedirect.onClick { startActivity(Intent(this, LoginActivity::class.java)) }
    }

    /**
     * onSignupSuccess
     * Actions to perform if the signup was successful is successful
     */
    override fun onSignupSuccess() {
        startActivity(Intent(this, ProfileDetailsActivity::class.java))
    }

    /**
     * onVerificationEmailSent
     * sends a verification email
     * to the user if the signup is successful
     * @param isSuccessful - If the email send  is successful
     * @param email - The email to be sent to
     */
    override fun onVerificationEmailSent(isSuccessful: Boolean, email: String) {
        showToast(this, getString(R.string.verification_email_sent,email))
    }

    /**
     * showSignupError
     * Show a signup error if the user could not be signed up successfully
     */
    override fun showSignupError() {
        showToast(this,getString(R.string.signup_unsuccessful) )
    }

    /**
     * showFirstNameError
     * Shows an error if the user first name is invalid
     */
    override fun showFirstNameError() {
        signupFirstName.error = getString(R.string.first_name_error)
    }

    /**
     * showLastNameError
     * Shows an error if the user last name is invalid
     */
    override fun showLastNameError() {
        signupLastName.error = getString(R.string.last_name_error)
    }


    /**
     * showPasswordError
     * Shows an error if the password is invalid
     */
    override fun showPasswordError() {
        signupPassword.error = getString(R.string.password_error, Constants.MIN_PASSWORD_LENGTH)
    }


    /**
     * showPasswordError
     * Shows an error if the repeated password is invalid
     */
    override fun showRepeatPasswordError() {
        signupPasswordRepeat.error = getString(R.string.password_error, Constants.MIN_PASSWORD_LENGTH)
    }

    /**
     * showEmailError
     * Shows an error if the users email is invalid
     */
    override fun showEmailError() {
        signupEmail.error = getString(R.string.email_error)
    }

    /**
     * showPasswordMatchingError
     * Displays a message if the users password and repeat password don't match
     */
    override fun showPasswordMatchingError() {
        signupPasswordRepeat.error = getString(R.string.repeat_password_error)
    }



}
