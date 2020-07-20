package com.acoria.cleanarchtictureexampleapp.nature.buildNature

import com.acoria.cleanarchtictureexampleapp.mvi.IResult
import com.acoria.cleanarchtictureexampleapp.mvi.IViewState
import com.acoria.cleanarchtictureexampleapp.nature.model.IPlant

object NatureStateFlow {
    //holds the state of a view
    data class ViewState(
        val searchBoxText: String? = null,
        val searchedPlantName: String = "",
        val searchedPlantMaxHeight: String = "",
        val searchedPlantReference: IPlant? = null,
        val searchedImage: String = "",
        val favoritesAdapterList: Map<IPlant, IPlantItemWrapper> = emptyMap(),
        val userCounter: String = "0"
    ) : IViewState

    //Action that is fire and forget: a one time event that does not keep state
//is based on SingleLiveEvent?
    sealed class Effect {
        data class ShowSnackbar(val message: String) : Effect()
        data class ShowToast(val message: String) : Effect()

        //or more specific:
        object AddedToFavoritesEffect : Effect()
        data class DeletedFromFavoritesEffect(val plant: IPlant) : Effect()
    }

    //all events/actions that a user can perform on the view
    sealed class Event {
        //data class for parameters, object for no parameters
        data class SearchPlantEvent(val searchedPlantName: String = "") : Event()
        data class DeletePlantFromFavoritesEvent(val plant: IPlant) : Event()
        object AddPlantToFavoritesEvent : Event()
    }

    sealed class Result : IResult {
        data class SearchPlantResult(val plant: IPlant? = null) : Result()
        data class AddToFavoriteListResult(val newFavoritePlant: IPlant?) : Result()
        data class DeletePlantFromFavoritesResult(val plant: IPlant) : Result()
        data class NewPlantRequestFlowResult(val counter: Int) : Result()
    }
}