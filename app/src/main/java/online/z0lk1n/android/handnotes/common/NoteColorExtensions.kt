package online.z0lk1n.android.handnotes.common

import android.content.Context
import android.support.v4.content.ContextCompat
import online.z0lk1n.android.handnotes.R
import online.z0lk1n.android.handnotes.data.entity.Note

fun Note.Color.getColorResId(context: Context) =
    ContextCompat.getColor(
        context, when (this) {
            Note.Color.WHITE -> R.color.white
            Note.Color.YELLOW -> R.color.yellow
            Note.Color.GREEN -> R.color.green
            Note.Color.BLUE -> R.color.blue
            Note.Color.RED -> R.color.red
            Note.Color.VIOLET -> R.color.violet
            Note.Color.PINK -> R.color.pink
        }
    )