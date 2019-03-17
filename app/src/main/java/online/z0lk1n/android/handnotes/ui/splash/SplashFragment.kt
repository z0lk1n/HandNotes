package online.z0lk1n.android.handnotes.ui.splash

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import online.z0lk1n.android.handnotes.R
import online.z0lk1n.android.handnotes.ui.base.BaseFragment

class SplashFragment : BaseFragment<Boolean?, SplashViewState>() {

    companion object {
        private const val START_DELAY = 1000L
    }

    override val viewModel: SplashViewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    private val navController by lazy {
        NavHostFragment.findNavController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
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
        navController.navigate(R.id.toMainFragment)
    }
}