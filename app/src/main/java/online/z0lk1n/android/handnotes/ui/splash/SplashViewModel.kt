package online.z0lk1n.android.handnotes.ui.splash

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import online.z0lk1n.android.handnotes.data.NotesRepository
import online.z0lk1n.android.handnotes.data.errors.NoAuthException
import online.z0lk1n.android.handnotes.ui.base.BaseViewModel

@ExperimentalCoroutinesApi
class SplashViewModel(private val repository: NotesRepository) : BaseViewModel<Boolean?>() {

    fun requestUser() {
        launch {
            repository.getCurrentUser()?.let {
                setData(true)
            } ?: setError(NoAuthException())
        }
    }
}