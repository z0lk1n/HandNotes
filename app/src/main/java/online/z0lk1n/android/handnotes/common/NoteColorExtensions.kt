package online.z0lk1n.android.handnotes.common

import android.content.Context
import android.support.v4.content.ContextCompat
import online.z0lk1n.android.handnotes.R
import online.z0lk1n.android.handnotes.data.entity.Note

fun Note.Color.getColorResId(context: Context) =
    ContextCompat.getColor(
        context, when (this) {
            Note.Color.WHITE -> R.color.white_note
            Note.Color.YELLOW -> R.color.yellow_note
            Note.Color.GREEN -> R.color.green_note
            Note.Color.BLUE -> R.color.blue_note
            Note.Color.RED -> R.color.red_note
            Note.Color.VIOLET -> R.color.violet_note
            Note.Color.PINK -> R.color.pink_note
        }
    )

fun Note.Color.getColorRes(): Int = when (this) {
    Note.Color.WHITE -> R.color.white_note
    Note.Color.VIOLET -> R.color.violet_note
    Note.Color.YELLOW -> R.color.yellow_note
    Note.Color.RED -> R.color.red_note
    Note.Color.PINK -> R.color.pink_note
    Note.Color.GREEN -> R.color.green_note
    Note.Color.BLUE -> R.color.blue_note
}