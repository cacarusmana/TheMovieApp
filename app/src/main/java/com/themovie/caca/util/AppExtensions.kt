package com.themovie.caca.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun AppCompatActivity.toast(errorMessage: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, errorMessage, duration).show()
}

fun Fragment.toast(errorMessage: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(activity, errorMessage, duration).show()
}

fun ViewGroup.inflate(resId: Int): View {
    return LayoutInflater.from(this.context).inflate(resId, this, false)
}

fun StringBuilder.appendSpace() {
    append(" ")
}

fun StringBuilder.appendAsterisk() {
    append("*")
}

fun StringBuilder.appendNewLine() {
    append("\n")
}