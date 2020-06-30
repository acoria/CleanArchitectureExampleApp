package com.acoria.cleanarchtictureexampleapp

import android.app.Application
import com.acoria.cleanarchtictureexampleapp.littleHelper.DispatcherProvider
import com.acoria.cleanarchtictureexampleapp.nature.IPlantRepository
import com.acoria.cleanarchtictureexampleapp.nature.ServiceLocator
import timber.log.Timber

class CustomApplication : Application() {

/**
 * An application that lazily provides a repository. Note that this Service Locator pattern is
 * used to simplify the sample. Consider a Dependency Injection framework.
 *
 */
    //TODO: Dependency Injection
    val plantRepository: IPlantRepository
        get() = ServiceLocator.createPlantRepository()

    val dispatcherProvider: DispatcherProvider
        get() = ServiceLocator.createDispatcherProvider()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}