package com.acoria.cleanarchtictureexampleapp.nature

import com.acoria.cleanarchtictureexampleapp.nature.model.IPlant

interface IPlantRepository{
    suspend fun searchForPlant(plantName: String) : IPlant?
}