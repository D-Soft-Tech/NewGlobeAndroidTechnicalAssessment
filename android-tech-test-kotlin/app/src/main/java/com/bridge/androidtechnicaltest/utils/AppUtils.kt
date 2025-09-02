package com.bridge.androidtechnicaltest.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.appcompat.app.AlertDialog
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.SampleData
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.dtos.requests.PupilRequestDTO

object AppUtils {
    fun base64StringToBitmap(base64String: String): Bitmap? =
        try {
            val imageBytes =
                Base64.decode(base64String.replace("data:image/.*;base64,", ""), Base64.DEFAULT)
            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        } catch (e: Exception) {
            null
        }

    fun getAQuickPupilRequestPayload(
        pupilId: Int,
        name: String,
        image: String
    ): PupilRequestDTO {
        val dummyLocation = SampleData.getRandomPupilLocation()
        return PupilRequestDTO(
            name,
            pupilId,
            image,
            AppConstants.DEFAULT_COUNTRY,
            dummyLocation.lat,
            dummyLocation.long
        )
    }

    fun Context.createAlertDialog(
        title: String,
        message: String,
        onCancelClicked: (() -> Unit)? = null,
        onOkClicked: () -> Unit
    ): AlertDialog = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
            dialog.cancel()
            onOkClicked.invoke()
        }
        .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.cancel()
            onCancelClicked?.invoke()
        }
        .create()
}