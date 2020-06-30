package com.acoria.cleanarchtictureexampleapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.acoria.cleanarchtictureexampleapp.littleHelper.DispatcherProvider
import com.acoria.cleanarchtictureexampleapp.nature.IPlantRepository
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.BuildNatureViewModel
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.NatureViewEffect
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.NatureViewEvent
import com.acoria.cleanarchtictureexampleapp.nature.model.IPlant
import kotlinx.coroutines.Dispatchers
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

    //next to a lot of other things, it supplies a main thread for unittesting
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        plantRepository = Mockito.mock(IPlantRepository::class.java)
        viewModel = BuildNatureViewModel(plantRepository, testDispatcherProvider)
    }

    //run blocking is needed for the repository suspend function
    @Test
    fun onAddPlantToFavorites() = runBlockingTest {

        //setup plan search result
        val searchTerm = "Some Plant"
        val plant = Mockito.mock(IPlant::class.java)
        Mockito.`when`(plantRepository.searchForPlant(searchTerm)).thenReturn(plant)
        Mockito.`when`(plant.name).thenReturn("Some name")
        Mockito.`when`(plant.maxHeight).thenReturn(10)
        Mockito.`when`(plant.imageUrl).thenReturn("Some url")

        viewModel.onEvent(NatureViewEvent.SearchPlantEvent(searchTerm))
        viewModel.onEvent(NatureViewEvent.AddPlantToFavoritesEvent)

        //https://medium.com/androiddevelopers/unit-testing-livedata-and-other-common-observability-problems-bb477262eb04
        //for a single value -> getOrAwaitValue
        assertEquals(
            viewModel.viewEffects.getOrAwaitValue(),
            NatureViewEffect.AddedToFavoritesEffect
        )
    }
}