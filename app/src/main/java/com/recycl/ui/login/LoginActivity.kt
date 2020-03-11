package com.recycl.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.recycl.R
import com.recycl.common.onClick
import com.recycl.common.onTextChanged
import com.recycl.loginPresenter
import com.recycl.ui.forgotpassword.ForgotPasswordActivity
import com.recycl.ui.main.MainActivity
import com.recycl.ui.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() , LoginView {


    private val presenter by lazy { loginPresenter() }

    private val TAG =  "LoginActivity";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter.setView(this)
        initUi()

    }

    private fun initUi() {
        loginEmail.onTextChanged { presenter.onEmailChanged(it) }
        loginPassword.onTextChanged{ presenter.onPasswordChanged(it) }
        loginButton.onClick { presenter.onLoginTap() }
        signupRedirect.onClick { startActivity(Intent(this, SignupActivity::class.java)) }
        forgotPasswordButton.onClick {
            Log.i(TAG, "Forgot password button clicked")
            startActivity(Intent(
            this, ForgotPasswordActivity::class.java)) }
    }

    override fun showPasswordError() {
        loginPassword.error = getString(R.string.login_password_error)
    }

    override fun showEmailError() {
        loginEmail.error = getString(R.string.login_email_error)
    }

    override fun onLoginSuccess() = startActivity(MainActivity.getLaunchIntent(this))
    override fun showLoginError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
    private fun login(email: String, password: String) {
        if(!validateForm()){
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
            if(task.isSuccessful){
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id",auth.currentUser?.email)
                startActivity(intent)

            } else {
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Authentication failed",Toast.LENGTH_SHORT).show()


            }
        })
    }
    private fun validateForm():Boolean {
        var valid = true

        val email = loginEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            loginEmail.error = "Required"
            valid = false;
        } else {
            loginEmail.error = null
        }

        val password = loginPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            loginPassword.error = "Required"
            valid = false;

        } else {
            loginPassword.error = null
        }

        return valid
    }

    override fun onClick(v: View) {
        val i = v.id
        when (i) {
            R.id.loginButton -> login(loginEmail.text.toString(), loginPassword.text.toString())
            R.id.signupRedirect -> startActivity(Intent(this,
                SignupActivity::class.java))
            R.id.forgotPassword -> startActivity(Intent(this,
                ForgotPasswordActivity::class.java))
        }
    }
*/
}
