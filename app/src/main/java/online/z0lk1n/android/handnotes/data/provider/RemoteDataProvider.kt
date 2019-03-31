package online.z0lk1n.android.handnotes.data.provider

import kotlinx.coroutines.channels.ReceiveChannel
import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.data.entity.User
import online.z0lk1n.android.handnotes.model.NoteResult

interface RemoteDataProvider {

    suspend fun getNoteById(id: String): Note

    suspend fun saveNote(note: Note): Note

    fun subscribeToAllNotes(): ReceiveChannel<NoteResult>

    suspend fun getCurrentUser(): User?

    suspend fun deleteNote(noteId: String)

    fun isHasUser(): Boolean
}