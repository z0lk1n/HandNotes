package online.z0lk1n.android.handnotes.data.entity

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Note(
    val id: String,
    val title: String,
    val text: String,
    val color: Color = Color.WHITE,
    val lastChanged: Date = Date()
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        enumValueOf<Color>(parcel.readString()!!),
        Date(parcel.readLong())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeString(color.name)
        parcel.writeLong(lastChanged.time)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Note> {

        override fun createFromParcel(parcel: Parcel) = Note(parcel)

        override fun newArray(size: Int): Array<Note?> = arrayOfNulls(size)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (id != other.id) return false

        return true
    }

    override fun hashCode() = id.hashCode()

    enum class Color {
        WHITE,
        YELLOW,
        GREEN,
        BLUE,
        RED,
        VIOLET,
        PINK
    }
}