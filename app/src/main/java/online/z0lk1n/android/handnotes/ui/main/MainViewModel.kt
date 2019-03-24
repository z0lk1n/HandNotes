package online.z0lk1n.android.handnotes.ui.main

import android.arch.lifecycle.Observer
import online.z0lk1n.android.handnotes.data.NotesRepository
import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.model.NoteResult
import online.z0lk1n.android.handnotes.ui.base.BaseViewModel

class MainViewModel(private val repository: NotesRepository) :
    BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = Observer<NoteResult> {
        it ?: let { return@Observer }

        when (it) {
            is NoteResult.Success<*> -> {
                viewStateLiveData.value = MainViewState(it.data as? List<Note>)
            }
            is NoteResult.Error -> {
                viewStateLiveData.value = MainViewState(error = it.error)
            }
        }
    }

    private val repositoryNotes = repository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
    }
}