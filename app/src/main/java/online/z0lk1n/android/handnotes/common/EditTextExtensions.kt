package online.z0lk1n.android.handnotes.common

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.*

//fixme drop app here
private fun getTextChangeWatcher(delay: Long, function: () -> Unit): TextWatcher {
    return object : TextWatcher {
        private var timer = Timer()

        override fun afterTextChanged(s: Editable?) {
            timer.cancel()
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    function()
                }
            }, delay)

            function()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
}

fun EditText.onChange(delay: Long, function: () -> Unit) =
    addTextChangedListener(getTextChangeWatcher(delay, function))

fun EditText.removeOnChange(delay: Long, function: () -> Unit) =
    removeTextChangedListener(getTextChangeWatcher(delay, function))
