package online.z0lk1n.android.handnotes.ui.note

import android.support.annotation.VisibleForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import online.z0lk1n.android.handnotes.data.NotesRepository
import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.ui.base.BaseViewModel

@ExperimentalCoroutinesApi
class NoteViewModel(private val repository: NotesRepository) :
    BaseViewModel<NoteData>() {

    private var note: Note? = null

    fun save(note: Note) {
        this.note = note
    }

    fun loadNote(noteId: String) {
        launch {
            try {
                repository.getNoteById(noteId).let {
                    note = it
                    setData(NoteData(note = it))
                }
            } catch (e: Throwable) {
                setError(e)
            }
        }
    }

    fun deleteNote() {
        launch {
            try {
                note?.let { repository.deleteNote(it.id) }
                note = null
                setData(NoteData(true))
            } catch (e: Throwable) {
                setError(e)
            }
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        launch {
            note?.let { repository.saveNote(it) }
            super.onCleared()
        }
    }
}