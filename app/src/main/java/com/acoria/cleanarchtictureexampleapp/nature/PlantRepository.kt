package com.acoria.cleanarchtictureexampleapp.nature

import com.acoria.cleanarchtictureexampleapp.nature.model.IPlant
import com.acoria.cleanarchtictureexampleapp.nature.model.Plant

class PlantRepository(private val drawableHelper: Map<String, Int>) {

    suspend fun searchForPlant(plantName: String) : IPlant? {
        //TODO - proper repository and more, such as DTOs
        var foundPlant: IPlant? = null
        val height = when(plantName){
            PLANT_NAME_SUNFLOWER ->  2
            PLANT_NAME_PALMTREE -> 20
            else -> 0
        }
        if(height != 0) {
            foundPlant = Plant(plantName, height, drawableHelper[plantName].toString())
        }
//        delay(2000)
        return foundPlant
    }
}