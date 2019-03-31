package online.z0lk1n.android.handnotes.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.github.ajalt.timberkt.Timber
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import online.z0lk1n.android.handnotes.data.errors.NoAuthException
import online.z0lk1n.android.handnotes.ui.main.MainActivity
import kotlin.coroutines.CoroutineContext

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
abstract class BaseFragment<S> : Fragment(), CoroutineScope {

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + Job()
    }

    private lateinit var dataJob: Job
    private lateinit var errorJob: Job

    abstract val model: BaseViewModel<S>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataJob = launch {
            model.getViewState().consumeEach { renderData(it) }
        }

        errorJob = launch {
            model.getErrorChannel().consumeEach { renderError(it) }
        }
    }

    override fun onDestroyView() {
        dataJob.cancel()
        errorJob.cancel()
        super.onDestroyView()
    }

    override fun onDestroy() {
        coroutineContext.cancel()
        super.onDestroy()
    }

    abstract fun renderData(data: S)

    protected fun renderError(error: Throwable?) {
        when (error) {
            is NoAuthException -> (activity as MainActivity).checkAuth()
            else -> error?.let { t ->
                Timber.e(t) { "${t.message}" }
                t.message?.let {
                    showError(it)
                }
            }
        }
    }

    protected fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }
}