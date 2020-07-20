package com.acoria.cleanarchtictureexampleapp

import com.acoria.cleanarchtictureexampleapp.mvi.IResult
import com.acoria.cleanarchtictureexampleapp.mvi.IViewState
import com.acoria.cleanarchtictureexampleapp.core.Lce
import com.acoria.cleanarchtictureexampleapp.mvi.IStateReducer
import com.acoria.cleanarchtictureexampleapp.mvi.IViewStateUpdater
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.IViewStateUpdaterFactory
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.viewState.ViewStateReducer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class ViewStateReducerUnitTest {

    private lateinit var viewStateUpdaterFactory: IViewStateUpdaterFactory<IViewState, Lce<IResult>>
    private lateinit var oldViewState: IViewState
    private lateinit var reducer : IStateReducer
    private lateinit var viewStateUpdater : IViewStateUpdater<IViewState, Lce<IResult>>

    @Before
    fun setup() {
        viewStateUpdaterFactory = Mockito.mock(IViewStateUpdaterFactory::class.java) as IViewStateUpdaterFactory<IViewState, Lce<IResult>>
        viewStateUpdater = Mockito.mock(IViewStateUpdater::class.java) as IViewStateUpdater<IViewState, Lce<IResult>>
        reducer = ViewStateReducer(viewStateUpdaterFactory)

        oldViewState = Mockito.mock(IViewState::class.java)
    }

    @Test
    fun reduceError() {
        val newViewState = Mockito.mock(IViewState::class.java)
        val result = Mockito.mock(IResult::class.java)
        val lceError = Lce.Error(result)
        Mockito.`when`(viewStateUpdaterFactory.createByLce(lceError)).thenReturn(viewStateUpdater)
        Mockito.`when`(viewStateUpdater.updateFromLceResult(oldViewState, lceError)).thenReturn(newViewState)

        assertEquals(reducer.reduce(oldViewState, lceError), newViewState)
    }
}