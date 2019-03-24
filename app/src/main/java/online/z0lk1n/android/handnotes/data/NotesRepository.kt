package online.z0lk1n.android.handnotes.data

import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.data.provider.RemoteDataProvider

class NotesRepository(private val remoteDataProvider: RemoteDataProvider) {

    fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)

    fun saveNote(note: Note) = remoteDataProvider.saveNote(note)

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()

    fun getCurrentUser() = remoteDataProvider.getCurrentUser()
}