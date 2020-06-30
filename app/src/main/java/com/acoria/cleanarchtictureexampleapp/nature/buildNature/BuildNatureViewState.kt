package com.acoria.cleanarchtictureexampleapp.nature.buildNature

import com.acoria.cleanarchtictureexampleapp.nature.model.IPlant

//holds the state of a view
data class NatureViewState(
    val searchBoxText: String? = null,
    val searchedPlantName: String = "",
    val searchedPlantMaxHeight: String = "",
    val searchedPlantReference: IPlant? = null,
    val searchedImage: String = "",
    val favoritesAdapterList: Map<IPlant, IPlantItemWrapper> = emptyMap()
)

//Action that is fire and forget: a one time event that does not keep state
//is based on SingleLiveEvent?
sealed class NatureViewEffect {
    data class ShowSnackbar(val message: String) : NatureViewEffect()
    data class ShowToast(val message: String) : NatureViewEffect()

    //or more specific:
    object AddedToFavoritesEffect : NatureViewEffect()
    data class DeletedFromFavoritesEffect(val plant: IPlant) :  NatureViewEffect()
}

//all events/actions that a user can perform on the view
sealed class NatureViewEvent {
    //data class for parameters, object for no parameters
    data class SearchPlantEvent(val searchedPlantName: String = "") : NatureViewEvent()
    data class DeletePlantFromFavoritesEvent(val plant : IPlant) : NatureViewEvent()
    object AddPlantToFavoritesEvent : NatureViewEvent()
}

sealed class NatureResult {
    data class SearchPlantResult(val plant: IPlant? = null) : NatureResult()
    data class AddToFavoriteListResult(val newFavoritePlant: IPlant?) : NatureResult()
    data class DeletePlantFromFavorites(val plant: IPlant) : NatureResult()
}