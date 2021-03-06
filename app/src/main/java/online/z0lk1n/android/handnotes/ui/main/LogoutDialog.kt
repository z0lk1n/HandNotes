package online.z0lk1n.android.handnotes.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import online.z0lk1n.android.handnotes.R

class LogoutDialog : DialogFragment() {

    interface LogoutListener {
        fun onLogout()
    }

    companion object {
        val TAG = LogoutDialog::class.java.name + "TAG"
        fun createInstance() = LogoutDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(context)
            .setTitle(R.string.logout_dialog_title)
            .setMessage(R.string.logout_dialog_message)
            .setPositiveButton(R.string.btn_ok) { _, _ ->
                (nav_host_fragment as LogoutListener).onLogout()
            }
            .setNegativeButton(R.string.btn_cancel) { _, _ -> dismiss() }
            .create()
}