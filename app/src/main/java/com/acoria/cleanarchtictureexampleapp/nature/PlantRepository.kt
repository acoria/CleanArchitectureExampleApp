package com.acoria.cleanarchtictureexampleapp.nature

import com.acoria.cleanarchtictureexampleapp.nature.model.IPlant
import com.acoria.cleanarchtictureexampleapp.nature.model.Plant
import kotlinx.coroutines.delay

class PlantRepository(private val drawableHelper: Map<String, Int>) : IPlantRepository{

    val favorites: List<IPlant> = emptyList()

    override suspend fun searchForPlant(plantName: String) : IPlant? {
        //TODO - proper repository and more, such as DTOs
        var id = when(plantName){
            PLANT_NAME_SUNFLOWER -> 1
            PLANT_NAME_PALMTREE -> 2
            else -> 0
        }
        var foundPlant: IPlant? = null
        val height = when(plantName){
            PLANT_NAME_SUNFLOWER ->  2
            PLANT_NAME_PALMTREE -> 20
            else -> 0
        }
        if(height != 0) {
            foundPlant = Plant(id, plantName, height, drawableHelper[plantName].toString())
        }
        delay(2000)
        return foundPlant
    }
}