package com.themovie.caca

import android.app.Application
import com.themovie.caca.di.networkModule
import com.themovie.caca.di.repositoryModule
import com.themovie.caca.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@MainApp)
            modules(arrayListOf(networkModule, repositoryModule, viewModelModule))
        }
    }
}