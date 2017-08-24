package com.example.joshskeen.stockwatcher.util

import android.support.v4.app.FragmentManager

private const val TAG_DIALOG_PROGRESS = "dialog_progress"

private val FragmentManager.dialog: ProgressDialogFragment?
    get() = findFragmentByTag(TAG_DIALOG_PROGRESS) as? ProgressDialogFragment

fun FragmentManager.showProgressDialog(message: String) {
    hideProgressDialog()
    ProgressDialogFragment.newInstance(message).apply {
        isCancelable = false
    }.show(this, TAG_DIALOG_PROGRESS)
}

fun FragmentManager.hideProgressDialog() = dialog?.dismissAllowingStateLoss()