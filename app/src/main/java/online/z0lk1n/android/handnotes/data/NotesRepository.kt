package online.z0lk1n.android.handnotes.data

import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.data.provider.RemoteDataProvider

class NotesRepository(private val remoteDataProvider: RemoteDataProvider) {

    suspend fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)

    suspend fun saveNote(note: Note) = remoteDataProvider.saveNote(note)

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()

    suspend fun getCurrentUser() = remoteDataProvider.getCurrentUser()

    suspend fun deleteNote(noteId: String) = remoteDataProvider.deleteNote(noteId)

    fun isHasUser() = remoteDataProvider.isHasUser()
}