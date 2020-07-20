package com.acoria.cleanarchtictureexampleapp

import com.acoria.cleanarchtictureexampleapp.core.Lce
import com.acoria.cleanarchtictureexampleapp.mvi.IResult
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.NatureStateFlow
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.viewState.ContentViewStateUpdater
import com.acoria.cleanarchtictureexampleapp.nature.model.Plant
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ContentViewStateUpdaterUnitTest {

    private val viewState = NatureStateFlow.ViewState()
    private lateinit var lce: Lce<IResult>
    private val plant = Plant(1, "PlantName", 2, "")
    private val oldViewState = NatureStateFlow.ViewState()

    @Before
    fun setup(){
        val result = NatureStateFlow.Result.SearchPlantResult(plant)
        lce = Lce.Content(result)
    }

    @Test
    fun updateFromLceResult(){
        val newViewState = ContentViewStateUpdater().updateFromLceResult(oldViewState, lce)
        assertEquals(
            newViewState.searchedPlantReference,
            plant
        )
    }

}