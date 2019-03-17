package online.z0lk1n.android.handnotes.ui.base

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.github.ajalt.timberkt.Timber
import online.z0lk1n.android.handnotes.R
import online.z0lk1n.android.handnotes.data.errors.NoAuthException

abstract class BaseFragment<T, S : BaseViewState<T>> : Fragment() {

    companion object {
        private const val RC_SIGN_IN = 666
    }

    abstract val viewModel: BaseViewModel<T, S>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getViewState().observe(this, Observer<S> { viewState ->
            viewState?.apply {
                data?.let { renderData(it) }
                error?.let { renderError(it) }
            }
        })
    }

    protected fun renderError(error: Throwable?) {
        when (error) {
            is NoAuthException -> startLoginActivity()
            else -> error?.let { t ->
                Timber.e(t) { "${t.message}" }
                t.message?.let {
                    showError(it)
                }
            }
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
                .setTheme(R.style.LoginStyle)
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK) {
//todo            finish()
        }
    }

    abstract fun renderData(data: T)

    protected fun showError(error: String) {
        //TODO change to snackbar
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }
}