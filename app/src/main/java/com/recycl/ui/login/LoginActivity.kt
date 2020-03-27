package com.recycl.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.recycl.R
import com.recycl.common.onClick
import com.recycl.common.onTextChanged
import com.recycl.common.showToast
import com.recycl.loginPresenter
import com.recycl.ui.forgotpassword.ForgotPasswordActivity
import com.recycl.ui.main.MainActivity
import com.recycl.ui.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * LoginActivity
 * @author Alexander Peebles
 * Student Number: 150328687
 * @see AppCompatActivity
 * @see LoginView
 * Activity used to display login form and information related to login requests
 */
class LoginActivity : AppCompatActivity() , LoginView {

    // Presenter used to handle button clicks
    private val presenter by lazy { loginPresenter() }



    /**
     * onCreate
     * Sets up the Activity user interface
     * and the presenter view
     * @param savedInstanceState - Activity's previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter.setView(this)
        initUi()

    }

    /**
     * initUi
     * Sets up the interactive components of the user interface
     */
    private fun initUi() {
        loginEmail.onTextChanged { presenter.onEmailChanged(it) }
        loginPassword.onTextChanged{ presenter.onPasswordChanged(it) }
        loginButton.onClick { presenter.onLoginTap() }
        signupRedirect.onClick { startActivity(Intent(this, SignupActivity::class.java)) }
        forgotPasswordButton.onClick {
            startActivity(Intent(this, ForgotPasswordActivity::class.java)) }
    }

    /**
     * showPasswordError
     * Shows an error if the password is incorrect
     */
    override fun showPasswordError() {
        loginPassword.error = getString(R.string.login_password_error)
    }

    /**
     * showEmailError
     * Shows an error if the email is not formatted correctly
     */
    override fun showEmailError() {
        loginEmail.error = getString(R.string.login_email_error)
    }

    /**
     * onLoginSuccess
     * Actions to perform if the login is successful
     */
    override fun onLoginSuccess() = startActivity(MainActivity.getLaunchIntent(this))

    /**
     * showLoginError
     * Displays an error to the user
     */
    override fun showLoginError() {
        showToast(this,getString(R.string.login_unsuccessful) )
    }

}
