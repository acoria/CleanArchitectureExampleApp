package com.acoria.cleanarchtictureexampleapp.nature.buildNature

import com.acoria.cleanarchtictureexampleapp.mvi.IStateReducer
import com.acoria.cleanarchtictureexampleapp.nature.Lce

class BuildNatureStateReducer : IStateReducer<NatureViewState, NatureResult> {
    override fun reduce(
        oldViewState: NatureViewState,
        result: Lce<NatureResult>
    ): NatureViewState {
        return when (result) {
            is Lce.Content -> {
                when (result.content) {
                    is NatureResult.SearchPlantResult -> {
                        val plant = result.content.plant
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
                    is NatureResult.AddToFavoriteListResult -> {
                        result.content.newFavoritePlant
                            ?.let {
                                val newAdapterList =
                                    oldViewState.favoritesAdapterList.toMutableMap()
                                newAdapterList.put(it, PlantItemWrapper())
                                oldViewState.copy(favoritesAdapterList = newAdapterList)
                            } ?: oldViewState.copy()
                    }
                    is NatureResult.DeletePlantFromFavorites -> {
                        val newAdapterList =
                            oldViewState.favoritesAdapterList.toMutableMap()

//                        val index = newAdapterList.indexOf(result.content.plant)
//                        newAdapterList.find { it.plant.equals(result.content.plantItemWrapper.plant) }
                        newAdapterList.remove(result.content.plant)
                        oldViewState.copy(favoritesAdapterList = newAdapterList)
                    }
                    is NatureResult.NewPlantRequestFlowResult -> {
                        oldViewState.copy(userCounter = result.content.counter.toString())
                    }
                }
            }
            is Lce.Loading -> {
                //TODO: show loading
                when (result.loadingContent) {
                    is NatureResult.DeletePlantFromFavorites -> {
                        val newAdapterList =
                            oldViewState.favoritesAdapterList.toMutableMap()
                        val plantToDelete = result.loadingContent.plant
                        newAdapterList.replace(plantToDelete, PlantItemWrapper(true))
                        oldViewState.copy(favoritesAdapterList = newAdapterList)
                    }
                    is NatureResult.SearchPlantResult -> {
                        oldViewState.copy(
                            searchedPlantName = "Searching..."
                        )
                    }
                    else -> oldViewState
                }
            }
            is Lce.Error -> {
                //TODO: error handling
                oldViewState
            }
        }
    }
}