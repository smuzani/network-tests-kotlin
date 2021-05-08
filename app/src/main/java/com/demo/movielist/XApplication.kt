package com.demo.movielist

import android.app.Application
import timber.log.Timber

class XApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}