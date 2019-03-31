package online.z0lk1n.android.handnotes.common

import java.text.SimpleDateFormat
import java.util.*

fun Date.toStringFormat(format: String) =
    SimpleDateFormat(format, Locale.getDefault()).format(this)!!