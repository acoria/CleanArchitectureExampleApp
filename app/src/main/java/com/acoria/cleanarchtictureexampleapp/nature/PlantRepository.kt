package com.acoria.cleanarchtictureexampleapp.nature

import com.acoria.cleanarchtictureexampleapp.nature.model.IPlant
import com.acoria.cleanarchtictureexampleapp.nature.model.Plant

class PlantRepository(private val drawableHelper: Map<String, Int>) {

    suspend fun searchForPlant(plantName: String) : IPlant? {
        //TODO
        var foundPlant: IPlant? = null
        val height = when(plantName){
            PLANT_NAME_SUNFLOWER ->  200
            PLANT_NAME_PALMTREE -> 2000
            else -> 0
        }
        if(height != 0) {
            foundPlant = Plant(plantName, height, drawableHelper[plantName].toString())
        }
//        delay(2000)
        return foundPlant
//        return Plant("Sun Flower", 200)
    }
}