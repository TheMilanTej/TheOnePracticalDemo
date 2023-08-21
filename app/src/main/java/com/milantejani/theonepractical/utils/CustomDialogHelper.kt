package com.milantejani.theonepractical.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.annotation.LayoutRes

class CustomDialogHelper(private val context: Context) {
    lateinit var customDialog: Dialog

    fun create(@LayoutRes layoutResId: Int, onCancelListener: (() -> Unit)? = null): View {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(layoutResId, null)

        customDialog = Dialog(context)
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setContentView(view)
        customDialog.setOnCancelListener {
            onCancelListener?.invoke()
        }

        return view
    }

    fun cancelOnTouch(boolean: Boolean) {
        customDialog.setCancelable(boolean)
        customDialog.setCanceledOnTouchOutside(boolean)
    }

    fun show() {
        if (!isShowing()) {
            customDialog.show()
        }
    }

    fun dismiss() {
        if (isShowing()) {
            customDialog.dismiss()
        }
    }

    fun isShowing() = customDialog.isShowing
}