package com.themovie.caca.di

import com.google.gson.GsonBuilder
import com.themovie.caca.BuildConfig
import com.themovie.caca.network.AppServices
import com.themovie.caca.repository.MovieRepository
import com.themovie.caca.util.Constant
import com.themovie.caca.viewmodel.MovieViewModel
import com.themovie.caca.viewmodel.NowPlayingViewModel
import com.themovie.caca.viewmodel.PopularViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    single { createOkHttpClient() }
    single { createGsonConverterFactory() }
    single { createRetrofit(get(), get()) }
    single { get<Retrofit>().create(AppServices::class.java) }
}

val repositoryModule = module {
    single { MovieRepository(appServices = get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(movieRepository = get()) }
    viewModel { NowPlayingViewModel(movieRepository = get()) }
    viewModel { PopularViewModel(movieRepository = get()) }
}


private fun createOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .connectTimeout(Constant.TIMEOUT_CONN, TimeUnit.SECONDS)
        .readTimeout(Constant.TIMEOUT_CONN, TimeUnit.SECONDS)
        .writeTimeout(Constant.TIMEOUT_CONN, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }).build()

private fun createGsonConverterFactory(): GsonConverterFactory {
    val gson = GsonBuilder().setDateFormat(Constant.RESPONSE_DATE_FORMAT).create()
    return GsonConverterFactory.create(gson)
}

private fun createRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit =
    Retrofit.Builder()
        .baseUrl(Constant.BASE_URL).client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()