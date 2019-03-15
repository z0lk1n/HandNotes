package online.z0lk1n.android.handnotes.ui.base

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.github.ajalt.timberkt.Timber

abstract class BaseFragment<T, S : BaseViewState<T>> : Fragment() {

    private val TAG = "${BaseFragment::class.java.simpleName}!"

    abstract val viewModel: BaseViewModel<T, S>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getViewState().observe(this, Observer<S> { viewState ->
            if (viewState == null) return@Observer
            if (viewState.data != null) renderData(viewState.data)
            if (viewState.error != null) renderError(viewState.error)
        })
    }

    protected fun renderError(error: Throwable?) {
        error?.let { e ->
            Timber.e(e) { "${e.message}" }
            e.message?.let {
                showError(it)
            }
        }
    }

    abstract fun renderData(data: T)

    protected fun showError(error: String) {
        //TODO change to snackbar
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }
}