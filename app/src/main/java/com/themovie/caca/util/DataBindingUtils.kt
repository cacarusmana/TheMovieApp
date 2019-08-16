package com.themovie.caca.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.themovie.caca.R
import com.vansuita.gaussianblur.GaussianBlur
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


@BindingAdapter("gaussianBlurImage")
fun gaussianBlurImage(view: ImageView, resId: Int) {
    GaussianBlur.with(view.context).put(resId, view)
}

@BindingAdapter("imageUrl")
fun imageUrl(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Glide.with(view.context)
            .load(Constant.BASE_IMAGE_URL + Constant.IMAGE_WIDTH_500_PARAM + it)
            .apply(RequestOptions().placeholder(R.drawable.image_place_holder_list).error(R.drawable.image_place_holder_list))
            .into(view)
    }

}

@BindingAdapter("src")
fun appSrc(view: ImageView, resId: Int) {
    view.setImageResource(resId)
}

fun Int.toFormattedNumber(): String {
    val otherSymbols = DecimalFormatSymbols(Locale.ENGLISH).apply {
        decimalSeparator = '.'
        groupingSeparator = ','
    }

    val formatter = DecimalFormat("#,##0;-#,##0", otherSymbols)

    return formatter.format(this)
}