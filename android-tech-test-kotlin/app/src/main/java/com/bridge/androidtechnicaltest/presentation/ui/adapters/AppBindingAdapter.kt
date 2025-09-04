package com.bridge.androidtechnicaltest.presentation.ui.adapters

import android.content.res.ColorStateList
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.presentation.viewModels.EditOrCreatePupilViewModel
import com.bridge.androidtechnicaltest.utils.AppUtils.base64StringToBitmap
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("android:loadImageUri")
fun ImageView.loadImageUri(imageUri: Uri?) {
    imageUri?.let {
        load(it) {
            crossfade(true)
            placeholder(R.drawable.animated_loader_drawable)
            fallback(R.drawable.boy)
            transformations(CircleCropTransformation())
        }
    } ?: run {
        load(R.drawable.boy) {
            transformations(CircleCropTransformation())
        }
    }
}

@BindingAdapter("android:loadImageFromUrl")
fun ImageView.loadImageFromUrl(imageUrl: String?) {
    imageUrl?.let {
        base64StringToBitmap(it)?.let { bitMap ->
            load(bitMap) {
                crossfade(true)
                placeholder(R.drawable.animated_loader_drawable)
                fallback(R.drawable.boy)
                transformations(CircleCropTransformation())
            }
        } ?: run { loadImageUri(null) }
    } ?: run {
        loadImageUri(null)
    }
}

@BindingAdapter("android:getInitials")
fun TextView.getInitials(pupilFullName: String?) {
    pupilFullName?.let {
        val initials = it.split(" ").let { arr ->
            "${arr.firstOrNull()?.firstOrNull()?.uppercase() ?: " "} ${
                arr.lastOrNull()?.firstOrNull()?.uppercase() ?: " "
            }"
        }
        text = initials
    } ?: run {
        text = "  "
    }
}

@BindingAdapter("pupilImageBase64", "defaultProfilePicContainer")
fun ImageView.loadProfilePictureBase64(
    pupilImageBase64: String?,
    defaultProfilePicContainer: ConstraintLayout
) {
    pupilImageBase64?.let {
        base64StringToBitmap(it)?.let { bitMap ->
            load(bitMap) {
                crossfade(true)
                placeholder(R.drawable.animated_loader_drawable)
                fallback(R.drawable.boy)
                transformations(CircleCropTransformation())
            }
        } ?: run {
            this.visibility = View.GONE
            defaultProfilePicContainer.visibility = View.VISIBLE
        }
    } ?: run {
        this.visibility = View.GONE
        defaultProfilePicContainer.visibility = View.VISIBLE
    }
}

@BindingAdapter("android:disableIfEditRecord")
fun View.disableIfEditRecord(pageTitle: String?) {
    pageTitle?.let {
        isEnabled = !it.contains("edit", true)
    }
}

@BindingAdapter("submitButton", "editOrCreateViewModel")
fun TextInputEditText.enableSubmitButton(
    submitButton: Button,
    editOrCreateViewModel: EditOrCreatePupilViewModel
) {
    this.doOnTextChanged { text, _, _, _ ->
        text?.let {
            if (it.isNotBlank()) {
                val hasInputChanged = editOrCreateViewModel.hasPupilRecordsBeenUpdated()
                if (hasInputChanged) {
                    submitButton.isEnabled = true
                }
            } else {
                submitButton.isEnabled = false
            }
        }
    }
}

@BindingAdapter("android:infoMessage")
fun TextInputLayout.infoMessage(textInputEditText: TextInputEditText) {
    textInputEditText.addTextChangedListener { text ->
        if (!text.isNullOrBlank()) {
            helperText = context.getString(R.string.this_id_is_not_final)
            setBoxStrokeColorStateList(
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.info_blue))
            )
        } else {
            helperText = null
            setBoxStrokeColorStateList(
                ColorStateList.valueOf(boxStrokeColor)
            )
        }
    }
}