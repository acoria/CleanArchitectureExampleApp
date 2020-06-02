package com.acoria.cleanarchtictureexampleapp.nature

import com.acoria.cleanarchtictureexampleapp.R

object ServiceLocator {

    private lateinit var plantRepositor: PlantRepository

    fun createPlantRepository() : PlantRepository {
        plantRepositor = PlantRepository(
            mapOf(
                PLANT_NAME_SUNFLOWER to R.drawable.sunflower,
                PLANT_NAME_PALMTREE to R.drawable.palmtree
            )
        )
        return plantRepositor
    }
}