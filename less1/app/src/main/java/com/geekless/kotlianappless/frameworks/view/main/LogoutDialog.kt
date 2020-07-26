package com.geekless.kotlianappless.frameworks.view.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.geekless.kotlianappless.R
import io.reactivex.subjects.PublishSubject


class LogoutDialog : DialogFragment() {

    companion object {
        val TAG = LogoutDialog::class.java.name + "TAG"
        fun createInstance() = LogoutDialog()
    }

    val publishSubject=PublishSubject.create<Boolean>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = AlertDialog.Builder(context!!)
        .setTitle(getString(R.string.logout_title))
        .setMessage(getString(R.string.logout_message))
        .setPositiveButton(R.string.logout_ok) { dialog, which ->
            publishSubject.onNext(true)
            publishSubject.onComplete()
        }
        .setNegativeButton(R.string.logout_cancel) { dialog, which ->
            dismiss()
        }
        .create()

}