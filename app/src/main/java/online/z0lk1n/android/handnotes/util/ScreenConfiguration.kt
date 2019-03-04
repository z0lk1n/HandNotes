package online.z0lk1n.android.handnotes.util

import android.content.Context
import android.content.res.Configuration

class ScreenConfiguration(private val context: Context) {

    fun calculateNumberOfColumns(): Int {
        return when {
            context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 2
        }
    }
}