package online.z0lk1n.android.handnotes.ui.splash

import online.z0lk1n.android.handnotes.data.NotesRepository
import online.z0lk1n.android.handnotes.data.errors.NoAuthException
import online.z0lk1n.android.handnotes.ui.base.BaseViewModel

class SplashViewModel(private val repository: NotesRepository) :
    BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        repository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                SplashViewState(true)
            } ?: SplashViewState(error = NoAuthException())
        }
    }
}