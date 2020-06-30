package com.acoria.cleanarchtictureexampleapp.nature

import com.acoria.cleanarchtictureexampleapp.R
import com.acoria.cleanarchtictureexampleapp.littleHelper.DispatcherProvider

object ServiceLocator {

    private lateinit var plantRepository: PlantRepository
    private lateinit var dispatcherProvider: DispatcherProvider

    fun createPlantRepository(): IPlantRepository {
        plantRepository = PlantRepository(
            mapOf(
                PLANT_NAME_SUNFLOWER to R.drawable.sunflower,
                PLANT_NAME_PALMTREE to R.drawable.palmtree
            )
        )
        return plantRepository
    }

    fun createDispatcherProvider(): DispatcherProvider {
        dispatcherProvider = DispatcherProvider()
        return dispatcherProvider
    }
}