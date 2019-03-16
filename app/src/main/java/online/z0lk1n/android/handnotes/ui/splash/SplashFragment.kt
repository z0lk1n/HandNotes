package online.z0lk1n.android.handnotes.ui.splash

import android.arch.lifecycle.ViewModelProviders
import android.os.Handler
import online.z0lk1n.android.handnotes.ui.base.BaseFragment

class SplashFragment : BaseFragment<Boolean?, SplashViewState>() {

    companion object {
        private const val START_DELAY = 1000L
    }

    override val viewModel: SplashViewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({ viewModel.requestUser() }, START_DELAY)
    }

    override fun renderData(data: Boolean?) {
        if (data == true) {
            startMainFragment()
        }
    }

    private fun startMainFragment() {
//        MainActivity.start(this)
    }
}