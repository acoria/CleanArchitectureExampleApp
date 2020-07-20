package com.acoria.cleanarchtictureexampleapp.nature.buildNature.viewState

import com.acoria.cleanarchtictureexampleapp.mvi.IResult
import com.acoria.cleanarchtictureexampleapp.mvi.IViewStateUpdater
import com.acoria.cleanarchtictureexampleapp.core.Lce
import com.acoria.cleanarchtictureexampleapp.mvi.IViewState
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.NatureStateFlow

internal class ErrorViewStateUpdater : IViewStateUpdater<NatureStateFlow.ViewState, Lce<IResult>>{

    override fun updateFromLceResult(
        oldViewState: NatureStateFlow.ViewState,
        lceResult: Lce<IResult>
    ): NatureStateFlow.ViewState {
        //TODO: error handling
        return oldViewState
    }
}