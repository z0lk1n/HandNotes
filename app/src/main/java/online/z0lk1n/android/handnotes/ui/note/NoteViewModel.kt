package online.z0lk1n.android.handnotes.ui.note

import online.z0lk1n.android.handnotes.data.NotesRepository
import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.model.NoteResult
import online.z0lk1n.android.handnotes.ui.base.BaseViewModel

class NoteViewModel(private val repository: NotesRepository = NotesRepository) :
    BaseViewModel<Note?, NoteViewState>() {

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            repository.saveNote(it)
        }
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever { result ->
            result ?: let { return@observeForever }

            when (result) {
                is NoteResult.Success<*> -> {
                    viewStateLiveData.value = NoteViewState(result.data as? Note)
                }
                is NoteResult.Error -> {
                    viewStateLiveData.value = NoteViewState(error = result.error)
                }
            }
        }
    }
}