package com.themovie.caca.model.response

import android.os.Parcelable
import com.themovie.caca.model.Genre
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenreResponse(
    val genres: List<Genre>
) : Parcelable