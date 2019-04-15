package online.z0lk1n.android.handnotes.util

import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue


class ScreenConfiguration(private val context: Context) {

    fun calculateNumberOfColumns(): Int {
        return when {
            context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 2
        }
    }

    fun getScreenWidth(): Int {
        val dip = context.resources.configuration.screenWidthDp

        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }
}