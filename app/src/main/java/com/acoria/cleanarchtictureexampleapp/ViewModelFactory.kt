package com.acoria.cleanarchtictureexampleapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.acoria.cleanarchtictureexampleapp.nature.PlantRepository
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.BuildNatureViewModel

class ViewModelFactory (
    private val plantRepository: PlantRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BuildNatureViewModel(plantRepository) as T
    }
}