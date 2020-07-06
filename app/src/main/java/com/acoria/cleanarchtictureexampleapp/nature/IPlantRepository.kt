package com.acoria.cleanarchtictureexampleapp.nature

import com.acoria.cleanarchtictureexampleapp.nature.model.IPlant
import kotlinx.coroutines.flow.*

interface IPlantRepository{
    suspend fun searchForPlant(plantName: String) : IPlant?
    fun plantRequestCounterFlow() : StateFlow<Int>
}