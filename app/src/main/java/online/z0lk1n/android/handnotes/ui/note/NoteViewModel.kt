package online.z0lk1n.android.handnotes.ui.note

import online.z0lk1n.android.handnotes.data.NotesRepository
import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.model.NoteResult
import online.z0lk1n.android.handnotes.ui.base.BaseViewModel

class NoteViewModel(private val repository: NotesRepository) :
    BaseViewModel<NoteViewState.Data, NoteViewState>() {

    private val currentNote: Note?
        get() = viewStateLiveData.value?.data?.note

    fun save(note: Note) {
        viewStateLiveData.postValue(NoteViewState(NoteViewState.Data(note = note)))
    }

    override fun onCleared() {
        currentNote?.let {
            repository.saveNote(it)
        }
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever { result ->
            result?.let {
                viewStateLiveData.value = when (it) {
                    is NoteResult.Success<*> -> {
                        NoteViewState(NoteViewState.Data(note = it.data as Note?))
                    }
                    is NoteResult.Error -> {
                        NoteViewState(error = it.error)
                    }
                }
            }
        }
    }

    fun deleteNote() {
        currentNote?.let { note ->
            repository.deleteNote(note.id).observeForever { result ->
                result?.let {
                    viewStateLiveData.value = when (it) {
                        is NoteResult.Success<*> -> {
                            NoteViewState(NoteViewState.Data(true))
                        }
                        is NoteResult.Error -> {
                            NoteViewState(error = it.error)
                        }
                    }
                }
            }
        }
    }
}