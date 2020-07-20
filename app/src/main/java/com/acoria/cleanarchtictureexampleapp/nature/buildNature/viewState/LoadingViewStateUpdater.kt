package com.acoria.cleanarchtictureexampleapp.nature.buildNature.viewState

import com.acoria.cleanarchtictureexampleapp.mvi.IViewStateUpdater
import com.acoria.cleanarchtictureexampleapp.mvi.IResult
import com.acoria.cleanarchtictureexampleapp.core.Lce
import com.acoria.cleanarchtictureexampleapp.mvi.IViewState
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.NatureStateFlow
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.PlantItemWrapper

internal class LoadingViewStateUpdater: IViewStateUpdater<NatureStateFlow.ViewState, Lce<IResult>> {

    override fun updateFromLceResult(
        oldViewState: NatureStateFlow.ViewState,
        lceResult: Lce<IResult>
    ): NatureStateFlow.ViewState {
        //TODO: show loading
        return when (val result = (lceResult as Lce.Loading).loadingContent) {
            is NatureStateFlow.Result.DeletePlantFromFavoritesResult -> {
                val newAdapterList =
                    oldViewState.favoritesAdapterList.toMutableMap()
                val plantToDelete = result.plant
                newAdapterList.replace(plantToDelete,
                    PlantItemWrapper(
                        true
                    )
                )
                oldViewState.copy(favoritesAdapterList = newAdapterList)
            }
            is NatureStateFlow.Result.SearchPlantResult -> {
                oldViewState.copy(
                    searchedPlantName = "Searching..."
                )
            }
            else -> oldViewState
        }
    }
}