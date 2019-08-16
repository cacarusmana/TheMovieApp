package com.themovie.caca.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(
    val companyName: String,
    val email: String,
    val country: String,
    val followers: Int,
    val following: Int,
    val logo: Int,
    val backdrop: Int
) : Parcelable