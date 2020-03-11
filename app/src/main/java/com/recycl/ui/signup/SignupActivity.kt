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


class SignupActivity : AppCompatActivity(), SignupView {


    private val presenter by lazy { signupPresenter() }

    private val TAG = "SignUpActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        presenter.setView(this)
        initUi()


    }

    private fun initUi() {
        signupFirstName.onTextChanged { presenter.onFirstNameChanged(it) }
        signupLastName.onTextChanged { presenter.onLastNameChanged(it) }
        signupEmail.onTextChanged { presenter.onEmailChanged(it) }
        signupPassword.onTextChanged { presenter.onPasswordChanged(it) }
        signupPasswordRepeat.onTextChanged { presenter.onRepeatPasswordChanged(it) }
        signupButton.onClick { presenter.onSignupTap() }
        loginRedirect.onClick { startActivity(Intent(this, LoginActivity::class.java)) }
    }

    override fun onSignupSuccess() {
        startActivity(Intent(this, ProfileDetailsActivity::class.java))
    }


    override fun onVerificationEmailSent(isSuccessful: Boolean, email: String) {
        // Make toast
    }
 /**

    override fun onSendVerificationEmail() {
        Toast.makeText(baseContext, "Verification email sent to " + user.getEmail(),Toast.LENGTH_SHORT).show()
*/

    override fun showSignupError() {
        showToast(this,getString(R.string.signup_unsuccessful) )
    }

    override fun showFirstNameError() {
        signupFirstName.error = getString(R.string.first_name_error)
    }

    override fun showLastNameError() {
        signupLastName.error = getString(R.string.last_name_error)
    }


    override fun showPasswordError() {
        signupPassword.error = getString(R.string.password_error, Constants.MIN_PASSWORD_LENGTH)
    }
    override fun showRepeatPasswordError() {
        signupPasswordRepeat.error = getString(R.string.password_error, Constants.MIN_PASSWORD_LENGTH)
    }


    override fun showEmailError() {
        signupEmail.error = getString(R.string.email_error)
    }

    override fun showPasswordMatchingError() {
        signupPasswordRepeat.error = getString(R.string.repeat_password_error)
    }



}

    /**

    private fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ) {
        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val userId = auth.currentUser!!.uid

                    verifyEmail()

                    val currentUserDb = dbReference.child(userId)
                    val user = User(firstName, lastName, email)
                    currentUserDb.child(userId).setValue(user)
                    val intent = Intent(this, ProfileDetailsActivity::class.java)
                    intent.putExtra("id", auth.currentUser?.email)
                    startActivity(intent)

                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()

                }
            }

    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = signupEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            signupEmail.error = "Required"
            valid = false
        } else {
            signupEmail.error = null
        }

        val password = signupPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            signupPassword.error = "Required"
            valid = false
        } else {
            signupPassword.error = null
        }

        val firstName = signupFirstName.text.toString()
        if (TextUtils.isEmpty(firstName)) {
            signupFirstName.error = "Required"
            valid = false
        } else {
            signupEmail.error = null
        }

        val lastName = signupLastName.text.toString()
        if (TextUtils.isEmpty(lastName)) {
            signupLastName.error = "Required"
            valid = false
        } else {
            signupLastName.error = null
        }

        return valid

    }
}
    /**
    private fun verifyEmail() {
        val user = auth.currentUser
        user!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if ( task.isSuccessful) {
                    Toast.makeText(baseContext, "Verification email sent to " + user.getEmail(),Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(baseContext, "Failed to send verification email.",Toast.LENGTH_SHORT).show()
                }
            }
    }




    /**
    override fun onClick(v: View) {
        when (v.id) {
            R.id.signupButton -> createAccount(signupEmail.text.toString(), signupPassword.text.toString(),signupFirstName.text.toString(),signupLastName.text.toString())
            R.id.loginRedirect -> startActivity(Intent(this,
                LoginActivity::class.java))
        }
    }*/
}
*/