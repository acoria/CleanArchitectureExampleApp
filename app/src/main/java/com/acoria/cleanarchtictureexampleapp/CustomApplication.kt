package com.acoria.cleanarchtictureexampleapp

import android.app.Application
import com.acoria.cleanarchtictureexampleapp.BuildConfig
import timber.log.Timber

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

}