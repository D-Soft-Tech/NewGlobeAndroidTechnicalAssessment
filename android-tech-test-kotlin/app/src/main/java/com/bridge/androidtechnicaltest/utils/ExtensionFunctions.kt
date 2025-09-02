package com.bridge.androidtechnicaltest.utils

import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.io.InputStream

object ExtensionFunctions {
    fun Fragment.showSnackBarMessage(message: String, isLong: Boolean = false) {
        requireParentFragment().view?.let {
            Snackbar.make(
                it, message,
                if (isLong) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    fun Uri.convertToString(context: Context): String {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(this)
            val bytes = inputStream?.readBytes() ?: byteArrayOf()
            inputStream?.close()
            Base64.encodeToString(bytes, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}