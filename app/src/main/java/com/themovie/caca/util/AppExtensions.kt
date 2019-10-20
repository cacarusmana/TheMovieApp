package com.themovie.caca.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.IOException
import java.net.SocketTimeoutException


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
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

fun <T> Single<T>.handleNetworkError(): Single<T> {
    return onErrorResumeNext { e ->
        when (e) {
            is SocketTimeoutException -> return@onErrorResumeNext Single.error(Exception("Connection timeout"))
            is retrofit2.HttpException -> {

                when (e.response().code()) {
                    in 400..499 -> {
                        val responseBody = e.response().errorBody()

                        responseBody?.let {
                            return@onErrorResumeNext Single.error(Exception(getErrorMessage(it)))
                        }.run {
                            return@onErrorResumeNext Single.error(e)
                        }
                    }
                    else -> return@onErrorResumeNext Single.error(e)
                }
            }
            is IOException -> return@onErrorResumeNext Single.error(Exception("Network error"))
            else -> return@onErrorResumeNext Single.error(e)
        }
    }
}

fun <T> Observable<T>.handleNetworkError(): Observable<T> {
    return onErrorResumeNext { e: Throwable ->
        when (e) {
            is SocketTimeoutException -> return@onErrorResumeNext Observable.error(Exception("Connection timeout"))
            is retrofit2.HttpException -> {

                when (e.response().code()) {
                    in 400..499 -> {
                        val responseBody = e.response().errorBody()

                        responseBody?.let {
                            return@onErrorResumeNext Observable.error(Exception(getErrorMessage(it)))
                        }.run {
                            return@onErrorResumeNext Observable.error(e)
                        }
                    }
                    else -> return@onErrorResumeNext Observable.error(e)
                }
            }
            is IOException -> return@onErrorResumeNext Observable.error(Exception("Network error"))
            else -> return@onErrorResumeNext Observable.error(e)
        }
    }
}

fun getErrorMessage(responseBody: ResponseBody): String? {
    return try {
        JSONObject(responseBody.string()).getString("status_message")
    } catch (e: Exception) {
        e.message
    }
}