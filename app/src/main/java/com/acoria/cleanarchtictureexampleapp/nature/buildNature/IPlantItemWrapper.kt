package com.acoria.cleanarchtictureexampleapp.nature.buildNature

import com.acoria.cleanarchtictureexampleapp.nature.model.IPlant

interface IPlantItemWrapper{
    val isBeingDeleted: Boolean
}

data class PlantItemWrapper(override val isBeingDeleted: Boolean = false): IPlantItemWrapper