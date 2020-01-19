package com.appsfactory.lastfm.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.appsfactory.lastfm.R


object Dialogs {

    fun showGeneralErrorDialog(
        context: Context, title: String, message: String, buttonText: String,
        cancelable: Boolean?, runnable: Runnable?
    ): Dialog {

        val dialog = Dialog(context, R.style.AppTheme)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.setContentView(R.layout.dialog_full_screen)
        (dialog.findViewById<View>(R.id.tv_title) as AppCompatTextView).text = title
        (dialog.findViewById<View>(R.id.tv_subtitle) as AppCompatTextView).text = message
        (dialog.findViewById<View>(R.id.btn_action) as AppCompatButton).text = buttonText

        dialog.findViewById<View>(R.id.btn_action).setOnClickListener { v ->
            runnable?.run()
            dialog.dismiss()
        }

        val lp = dialog.window?.attributes
        lp?.width = WindowManager.LayoutParams.MATCH_PARENT
        lp?.height = WindowManager.LayoutParams.MATCH_PARENT

        dialog.window?.attributes = lp
        cancelable?.let { dialog.setCancelable(it) }
        return dialog
    }

}