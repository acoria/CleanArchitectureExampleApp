package com.acoria.cleanarchtictureexampleapp

import android.app.Application
import com.acoria.cleanarchtictureexampleapp.BuildConfig
import com.acoria.cleanarchtictureexampleapp.nature.PlantRepository
import com.acoria.cleanarchtictureexampleapp.nature.ServiceLocator
import timber.log.Timber

class CustomApplication : Application() {

/**
 * An application that lazily provides a repository. Note that this Service Locator pattern is
 * used to simplify the sample. Consider a Dependency Injection framework.
 *
 */
    //TODO: Dependency Injection
    val plantRepository: PlantRepository
        get() = ServiceLocator.createPlantRepository()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}