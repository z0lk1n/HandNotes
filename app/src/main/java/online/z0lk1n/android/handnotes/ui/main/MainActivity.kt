package online.z0lk1n.android.handnotes.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.Navigation
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_failure.*
import online.z0lk1n.android.handnotes.R
import online.z0lk1n.android.handnotes.data.NotesRepository
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 666
    }

    private val repository: NotesRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        checkAuth()
    }

    fun checkAuth() {
        if (repository.isHasUser()) {
            setContentView(R.layout.activity_main)
        } else {
            startLoginActivity()
        }
    }

    private fun startLoginActivity() {
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.AnonymousBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.android_robot)
                .setTheme(R.style.AppTheme_Login)
                .setAvailableProviders(providers)
                .build(),
            MainActivity.RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                checkAuth()
            } else {
                showReconnectLayout()
            }
        }
    }

    private fun showReconnectLayout() {
        setContentView(R.layout.activity_failure)
        btn_failure.setOnClickListener { startLoginActivity() }
    }

    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
}