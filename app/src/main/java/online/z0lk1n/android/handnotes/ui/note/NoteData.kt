package online.z0lk1n.android.handnotes.ui.note

import online.z0lk1n.android.handnotes.data.entity.Note

//todo 30.03.2019 maybe move in data layer
data class NoteData(val isDeleted: Boolean = false, val note: Note? = null)