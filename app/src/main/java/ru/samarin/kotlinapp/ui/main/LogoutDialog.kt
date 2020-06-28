package ru.samarin.kotlinapp.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import ru.samarin.kotlinapp.R

class LogoutDialog() : DialogFragment() {

    var onLogout: (() -> Unit)? = null

    companion object {

        val TAG = LogoutDialog::class.java.name + "_TAG"

        fun createInstance(onLogout: (() -> Unit)) = LogoutDialog().apply {
            this.onLogout = onLogout
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = AlertDialog.Builder(context)
        .setTitle(getString(R.string.exit))
        .setMessage(getString(R.string.dialog_message))
        .setPositiveButton(getString(R.string.yes)) { dialog, which -> onLogout?.invoke() }
        .setNegativeButton(getString(R.string.no)) { dialog, which -> dismiss() }
        .create()
}