package com.github.dhaval2404.imagepicker.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import com.example.libraryimagepicker.R
import com.example.libraryimagepicker.constant.ImageProvider
import com.example.libraryimagepicker.listener.DismissListener
import com.example.libraryimagepicker.listener.ResultListener

internal object DialogHelper {

    fun showChooseAppDialog(
        context: Context,
        listener: ResultListener<ImageProvider>,
        dismissListener: DismissListener?
    ) {

        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_choose_app)

        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        if (dialog.window != null) {
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        dialog.setCancelable(true)
        dialog.findViewById<View>(R.id.camera).setOnClickListener {
            listener.onResult(ImageProvider.CAMERA)
            dialog.dismiss()
        }

        dialog.findViewById<View>(R.id.image).setOnClickListener {
            listener.onResult(ImageProvider.GALLERY)
            dialog.dismiss()
        }

        dialog.findViewById<View>(R.id.close).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}
