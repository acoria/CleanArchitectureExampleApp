package com.acoria.cleanarchtictureexampleapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.acoria.cleanarchtictureexampleapp.littleHelper.DispatcherProvider
import com.acoria.cleanarchtictureexampleapp.mvi.IStateReducer
import com.acoria.cleanarchtictureexampleapp.mvi.IViewState
import com.acoria.cleanarchtictureexampleapp.nature.IPlantRepository
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.BuildNatureViewModel
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.NatureStateFlow
import com.acoria.cleanarchtictureexampleapp.nature.model.IPlant
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class BuildNatureViewModelUnitTest {

    private val testDispatcherProvider = DispatcherProvider(
        //unconfined -> runs in the thread it was originally spawned from
        IO = Dispatchers.Unconfined
    )
    private lateinit var plantRepository: IPlantRepository
    private lateinit var viewModel: BuildNatureViewModel
    private lateinit var stateFlow: StateFlow<*>
    private lateinit var stateReducer: IStateReducer
    private val newViewState = NatureStateFlow.ViewState()

    //next to a lot of other things, it supplies a main thread for unittesting
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        stateFlow = Mockito.mock(StateFlow::class.java)

        plantRepository = Mockito.mock(IPlantRepository::class.java)
        Mockito.`when`(plantRepository.plantRequestCounterFlow())
            .thenReturn(stateFlow as StateFlow<Int>)

        stateReducer = Mockito.mock(IStateReducer::class.java)

        viewModel = BuildNatureViewModel(plantRepository, testDispatcherProvider, stateReducer)
    }

    //run blocking is needed for the repository suspend function
    @Test
    fun onAddPlantToFavoritesEffect() = runBlockingTest {

        setupPlantSearchResult()
        viewModel.onEvent(NatureStateFlow.Event.AddPlantToFavoritesEvent)

        //https://medium.com/androiddevelopers/unit-testing-livedata-and-other-common-observability-problems-bb477262eb04
        //for a single value -> getOrAwaitValue
        assertEquals(
            viewModel.viewEffects.getOrAwaitValue(),
            NatureStateFlow.Effect.AddedToFavoritesEffect
        )
    }

    @Test
    fun onAddPlantToFavoritesEvent() = runBlockingTest {
        setupPlantSearchResult()
        Mockito.`when`(stateReducer.reduce(any(), any())).thenReturn(newViewState)
        viewModel.onEvent(NatureStateFlow.Event.AddPlantToFavoritesEvent)

        //for multiple values, add a block
        viewModel.viewState.observeForTesting {
            viewModel.onEvent(NatureStateFlow.Event.AddPlantToFavoritesEvent)
            assertEquals(viewModel.viewState.value, newViewState)
        }
    }

    private suspend fun setupPlantSearchResult() {
        //setup plan search result
        val searchTerm = "Some Plant"
        val plant = Mockito.mock(IPlant::class.java)
//        Mockito.`when`(plantRepository.searchForPlant(searchTerm)).thenReturn(plant)
//        Mockito.`when`(plant.name).thenReturn("Some name")
//        Mockito.`when`(plant.maxHeight).thenReturn(10)
//        Mockito.`when`(plant.imageUrl).thenReturn("Some url")

        val inBetweenViewState = NatureStateFlow.ViewState(searchedPlantReference = plant)
        Mockito.`when`(stateReducer.reduce(any(), any())).thenReturn(inBetweenViewState)

        viewModel.onEvent(NatureStateFlow.Event.SearchPlantEvent(searchTerm))
    }


}