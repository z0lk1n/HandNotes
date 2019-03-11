package online.z0lk1n.android.handnotes.ui.note

import android.arch.lifecycle.ViewModel
import online.z0lk1n.android.handnotes.data.NotesRepository
import online.z0lk1n.android.handnotes.data.entity.Note

class NoteViewModel(private val repository: NotesRepository = NotesRepository) : ViewModel() {

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            repository.saveNote(it)
        }
    }
}