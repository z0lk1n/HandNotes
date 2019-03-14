package online.z0lk1n.android.handnotes.data.provider

import android.arch.lifecycle.LiveData
import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.model.NoteResult

interface RemoteDataProvider {

    fun getNoteById(id: String): LiveData<NoteResult>

    fun saveNote(note: Note): LiveData<NoteResult>

    fun subscribeToAllNotes(): LiveData<NoteResult>
}