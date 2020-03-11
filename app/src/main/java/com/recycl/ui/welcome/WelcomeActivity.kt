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

class WelcomeActivity : AppCompatActivity(), WelcomeView {

    private val presenter by lazy { welcomePresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        presenter.setView(this)
        presenter.viewReady()

        welcomeLoginButton.onClick {startActivity(Intent(this, LoginActivity::class.java))}
        welcomeSignupButton.onClick {startActivity(Intent(this, SignupActivity::class.java))}

    }

    override fun startMainScreen() = startActivity(MainActivity.getLaunchIntent(this))
}
