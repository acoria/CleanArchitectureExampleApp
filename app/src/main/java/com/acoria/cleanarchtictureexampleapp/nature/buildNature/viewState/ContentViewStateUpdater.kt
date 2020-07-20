package com.acoria.cleanarchtictureexampleapp.nature.buildNature.viewState

import com.acoria.cleanarchtictureexampleapp.mvi.IViewStateUpdater
import com.acoria.cleanarchtictureexampleapp.mvi.IResult
import com.acoria.cleanarchtictureexampleapp.core.Lce
import com.acoria.cleanarchtictureexampleapp.mvi.IViewState
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.NatureStateFlow
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.PlantItemWrapper

internal class ContentViewStateUpdater : IViewStateUpdater<NatureStateFlow.ViewState, Lce<IResult>> {

    override fun updateFromLceResult(
        oldViewState: NatureStateFlow.ViewState,
        lceResult: Lce<IResult>
    ): NatureStateFlow.ViewState {
        return when (val result = (lceResult as Lce.Content).content) {
            is NatureStateFlow.Result.SearchPlantResult -> {
                val plant = result.plant
                if (plant != null) {
                    oldViewState.copy(
                        searchBoxText = plant.name,
                        searchedPlantName = plant.name,
                        searchedPlantMaxHeight = plant.maxHeight.toString(),
                        searchedPlantReference = plant,
                        searchedImage = plant.imageUrl
                    )
                } else {
                    oldViewState.copy(
                        searchedPlantName = "",
                        searchedPlantMaxHeight = "",
                        searchedPlantReference = null,
                        searchedImage = ""
                    )
                }

            }
            is NatureStateFlow.Result.AddToFavoriteListResult -> {
                result.newFavoritePlant
                    ?.let {
                        val newAdapterList =
                            oldViewState.favoritesAdapterList.toMutableMap()
                        newAdapterList.put(it, PlantItemWrapper())
                        oldViewState.copy(favoritesAdapterList = newAdapterList)
                    } ?: oldViewState.copy()
            }
            is NatureStateFlow.Result.DeletePlantFromFavoritesResult -> {
                val newAdapterList =
                    oldViewState.favoritesAdapterList.toMutableMap()
                newAdapterList.remove(result.plant)
                oldViewState.copy(favoritesAdapterList = newAdapterList)
            }
            is NatureStateFlow.Result.NewPlantRequestFlowResult -> {
                oldViewState.copy(userCounter = result.counter.toString())
            }
            else -> throw NotImplementedError()
        }
    }
}