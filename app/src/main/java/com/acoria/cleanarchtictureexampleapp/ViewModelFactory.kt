package com.acoria.cleanarchtictureexampleapp

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.acoria.cleanarchtictureexampleapp.littleHelper.DispatcherProvider
import com.acoria.cleanarchtictureexampleapp.nature.IPlantRepository
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.BuildNatureViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val plantRepository: IPlantRepository,
    private val dispatcherProvider: DispatcherProvider,
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
                BuildNatureViewModel(plantRepository, dispatcherProvider)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}