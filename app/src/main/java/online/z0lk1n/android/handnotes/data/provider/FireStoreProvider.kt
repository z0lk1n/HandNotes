package online.z0lk1n.android.handnotes.data.provider

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.Timber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.data.entity.User
import online.z0lk1n.android.handnotes.data.errors.NoAuthException
import online.z0lk1n.android.handnotes.model.NoteResult

class FireStoreProvider(
    private val firebaseAuth: FirebaseAuth,
    private val store: FirebaseFirestore
) : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }

    private val currentUser
        get() = firebaseAuth.currentUser

    override fun getCurrentUser(): LiveData<User?> =
        MutableLiveData<User?>().apply {
            value = currentUser?.let { User(it.displayName ?: "") }
        }

    private fun getUserNotesCollection() = currentUser?.let {
        store.collection(USERS_COLLECTION)
            .document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override fun getNoteById(id: String) = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().document(id).get()
                .addOnSuccessListener {
                    value = NoteResult.Success(it.toObject(Note::class.java))
                }.addOnFailureListener {
                    Timber.e(it) { "Error reading note with id: $id" }
                    throw it
                }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun saveNote(note: Note) = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().document(note.id).set(note)
                .addOnSuccessListener {
                    Timber.d { "Note $note is saved" }
                    value = NoteResult.Success(note)
                }.addOnFailureListener {
                    Timber.e(it) { "Error saving note $note, message: ${it.message}" }
                    throw it
                }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun subscribeToAllNotes() = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().addSnapshotListener { snapshot, e ->
                value = e?.let { NoteResult.Error(e) }
                    ?: snapshot?.let {
                        val notes = it.documents.map { d ->
                            d.toObject(Note::class.java)
                        }
                        NoteResult.Success(notes)
                    }
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun deleteNote(noteId: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            getUserNotesCollection().document(noteId).delete()
                .addOnSuccessListener {
                    value = NoteResult.Success(null)
                }
                .addOnFailureListener {
                    value = NoteResult.Error(it)
                }
        }
}