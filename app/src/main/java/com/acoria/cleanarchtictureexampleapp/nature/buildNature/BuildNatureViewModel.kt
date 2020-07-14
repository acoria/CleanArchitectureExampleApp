package com.acoria.cleanarchtictureexampleapp.nature.buildNature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acoria.cleanarchtictureexampleapp.littleHelper.DispatcherProvider
import com.acoria.cleanarchtictureexampleapp.mvi.IResult
import com.acoria.cleanarchtictureexampleapp.mvi.IStateReducer
import com.acoria.cleanarchtictureexampleapp.mvi.IViewState
import com.acoria.cleanarchtictureexampleapp.nature.IPlantRepository
import com.acoria.cleanarchtictureexampleapp.nature.Lce
import com.acoria.cleanarchtictureexampleapp.nature.model.IPlant
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber


class BuildNatureViewModel(
    private val plantRepo: IPlantRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val stateReducer : IStateReducer<NatureViewState, NatureResult> = BuildNatureStateReducer()
) : ViewModel() {

    //--View State--
    private var _viewStateLiveData = MutableLiveData<NatureViewState>()

    //separate property so it can be exposed as immutable
    val viewState: LiveData<NatureViewState>
        get() = _viewStateLiveData

    //store view state so it can be easily copied
    private var currentViewState =
        NatureViewState()
        set(value) {
            field = value
            //use postValue instead of setter -> switches to the Main Thread automatically
            _viewStateLiveData.postValue(value)
//            _viewStateLiveData.value = value
        }

    init {
        viewModelScope.launch {
            plantRepo.plantRequestCounterFlow()
                .map { Lce.Content(NatureResult.NewPlantRequestFlowResult(it)) as Lce<NatureResult> }
                .collect {
                    resultToViewState(it)
                }
        }
    }

    //--View Effect--
    private val _viewEffectLiveData = MutableLiveData<NatureViewEffect>()
    val viewEffects: LiveData<NatureViewEffect>
        get() = _viewEffectLiveData

    private var searchPlantInRepoJob: Job? = null

    fun onEvent(event: NatureViewEvent) {
        Timber.d("##event $event")

        when (event) {
            is NatureViewEvent.AddPlantToFavoritesEvent -> {
                onAddPlantToFavorites()
            }
            is NatureViewEvent.SearchPlantEvent -> {
                onSearchPlant(event.searchedPlantName)
            }
            is NatureViewEvent.DeletePlantFromFavoritesEvent -> {
                onDeletePlantFromFavorites(event.plant)
            }
        }
    }

    private fun onDeletePlantFromFavorites(plant: IPlant) {
        val result = NatureResult.DeletePlantFromFavorites(plant)
        resultToViewState(Lce.Loading(result))

        viewModelScope.launch {
            delay(2000)
            resultToViewState(Lce.Content(result))
            resultToViewEffect(Lce.Content(result))
        }
    }

    private fun onAddPlantToFavorites() {
        val plant = currentViewState.searchedPlantReference
        if (plant == null) {
            Timber.w("could not find searched plant reference : $plant")
            return
        }

        val result = currentViewState.favoritesAdapterList.get(plant)?.let {
            //already in the list, nothing to add
            Lce.Content(
                NatureResult.AddToFavoriteListResult(
                    null
                )
            ) as Lce<NatureResult>

        } ?:
        //hand over the result so it can be added
        Lce.Content(
            NatureResult.AddToFavoriteListResult(
                plant
            )
        ) as Lce<NatureResult>

        resultToViewState(result)
        resultToViewEffect(result)
    }

    private fun onSearchPlant(searchedPlantName: String) {

        resultToViewState(Lce.Loading(NatureResult.SearchPlantResult()))

        if (searchPlantInRepoJob?.isActive == true) searchPlantInRepoJob?.cancel()

        //this coroutine handles the execution within another thread (default is Main)
        searchPlantInRepoJob = viewModelScope.launch(dispatcherProvider.IO) {
            val foundPlant = plantRepo.searchForPlant(searchedPlantName)
//            if (foundPlant == null) {
//                resultToViewEffect(Lce.Error(NatureResult.ToastResult("There is no result for '$searchedPlantName'")))
//            }

            resultToViewState(
                Lce.Content(
                    NatureResult.SearchPlantResult(
                        foundPlant
                    )
                )
            )
        }
    }

    private fun onScreenLoad() {
        resultToViewState(Lce.Loading())
    }

    private fun resultToViewEffect(result: Lce<NatureResult>) {
        Timber.d("##resultToEffect $result")

        if (result is Lce.Content && result.content is NatureResult.AddToFavoriteListResult) {
            _viewEffectLiveData.value =
                NatureViewEffect.AddedToFavoritesEffect
        } else if (result is Lce.Content && result.content is NatureResult.DeletePlantFromFavorites) {
            _viewEffectLiveData.value =
                NatureViewEffect.DeletedFromFavoritesEffect(result.content.plant)
        } else if (result is Lce.Error) {
            Timber.d("##resultToEffect Lce.Error")
            _viewEffectLiveData.value =
                NatureViewEffect.ShowToast(
                    "Error :("
                )
        } else {
            Timber.d("##resultToEffect error -> not implemented")
        }
    }

    private fun resultToViewState(result: Lce<NatureResult>) {
        Timber.d("##resultToViewState $result")
        currentViewState = stateReducer.reduce(currentViewState, result)
    }

    override fun onCleared() {
        super.onCleared()
        //watch out for leaks: https://medium.com/androiddevelopers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54
        //drop any callbacks to the view model from components that exist in the entire application/are scoped to it
        //e.g. a repository: If the repository is holding a reference to a callback in the ViewModel, the ViewModel will be temporarily leaked
        //TODO: not sure if this is enough:
        if (searchPlantInRepoJob?.isActive == true) searchPlantInRepoJob?.cancel()
    }
}