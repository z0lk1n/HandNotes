package online.z0lk1n.android.handnotes.data

import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.data.provider.FireStoreProvider
import online.z0lk1n.android.handnotes.data.provider.RemoteDataProvider

object NotesRepository {

    private val remoteDataProvider: RemoteDataProvider = FireStoreProvider()

    fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)
    fun saveNote(note: Note) = remoteDataProvider.saveNote(note)
    fun getNotes() = remoteDataProvider.subscribeToAllNotes()
}