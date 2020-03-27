package com.recycl.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.recycl.R
import com.recycl.common.onClick
import com.recycl.ui.login.LoginActivity
import com.recycl.ui.main.MainActivity
import com.recycl.ui.signup.SignupActivity
import com.recycl.welcomePresenter
import kotlinx.android.synthetic.main.activity_welcome.*

/**
 * WelcomeActivity
 * @author Alexander Peebles
 * Student Number: 150328687
 * Activity to display welcome screen prompting user to signup or login
 */
class WelcomeActivity : AppCompatActivity(), WelcomeView {

    // Presenter used to handle button clicks
    private val presenter by lazy { welcomePresenter() }

    /**
     * onCreate
     * Sets up the Activity user interface
     * and the presenter view
     * @param savedInstanceState - Activity's previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        presenter.setView(this)
        presenter.viewReady()

        welcomeLoginButton.onClick {startActivity(Intent(this, LoginActivity::class.java))}
        welcomeSignupButton.onClick {startActivity(Intent(this, SignupActivity::class.java))}

    }

    /**
     * startMainScreen
     * Goes straight to MainActivity if the user is logged in
     */
    override fun startMainScreen() = startActivity(MainActivity.getLaunchIntent(this))
}
