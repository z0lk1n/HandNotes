package online.z0lk1n.android.handnotes.common

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.*

fun EditText.onChange(delay: Long, function: () -> Unit) =
    addTextChangedListener(object : TextWatcher {
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
    })
