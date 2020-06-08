package com.acoria.cleanarchtictureexampleapp

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.acoria.cleanarchtictureexampleapp.nature.PlantRepository
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.BuildNatureViewModel

class ViewModelFactory constructor(
    private val plantRepository: PlantRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(BuildNatureViewModel::class.java) ->
                BuildNatureViewModel(plantRepository)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}

//class ViewModelFactory (
//    private val plantRepository: PlantRepository
//): ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return BuildNatureViewModel(plantRepository) as T
//    }
//}