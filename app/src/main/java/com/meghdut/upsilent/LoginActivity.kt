package com.meghdut.upsilent

//import com.meghdut.upsilent.utils.navigateToMainActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chibatching.kotpref.Kotpref
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.meghdut.upsilent.db.LocalDb
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 12
    override fun onCreate(savedInstanceState: Bundle?) {
        Kotpref.init(this)
        super.onCreate(savedInstanceState)
        if (LocalDb.isLoggedIn) {
            navigateToHomeActivity(this)
        }
        setContentView(R.layout.activity_login)
    }

    private val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
    }
    private val mGoogleSignInClient by lazy { GoogleSignIn.getClient(this, gso) }

    override fun onResume() {
        super.onResume()
        sign_in_button.setOnClickListener {
            sigin()
        }
    }

    private fun sigin() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            println("in.iot.lab.ghouse.ui.main>>LoginFragment>handleSignInResult  signInResult:failed code=${e.statusCode}")
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            val activity = this

            LocalDb.apply {
                isLoggedIn = true
                name = account.displayName ?: " None"
                email = account.email ?: "None"
                navigateToHomeActivity(activity)
            }
        } else {
            Snackbar.make(imageView, "Login Failed", Snackbar.LENGTH_LONG).show()
        }
    }


    private fun navigateToHomeActivity(loginActivity: AppCompatActivity) {
        val intent = Intent(loginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}