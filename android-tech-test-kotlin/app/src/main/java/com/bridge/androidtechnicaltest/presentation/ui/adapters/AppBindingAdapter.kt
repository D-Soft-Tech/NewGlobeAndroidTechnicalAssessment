package com.bridge.androidtechnicaltest.presentation.ui.adapters

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.utils.AppUtils.base64StringToBitmap

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
        isClickable = !it.contains("edit", true)
        isFocusable = !it.contains("edit", true)
        isEnabled = !it.contains("edit", true)
    }
}