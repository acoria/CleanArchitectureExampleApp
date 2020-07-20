package com.acoria.cleanarchtictureexampleapp.nature.buildNature.viewState

import com.acoria.cleanarchtictureexampleapp.mvi.IStateReducer
import com.acoria.cleanarchtictureexampleapp.mvi.IResult
import com.acoria.cleanarchtictureexampleapp.mvi.IViewState
import com.acoria.cleanarchtictureexampleapp.core.Lce
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.IViewStateUpdaterFactory

class ViewStateReducer(
    private val viewStateUpdater: IViewStateUpdaterFactory<IViewState, Lce<IResult>> = ViewStateUpdaterFactory()
) : IStateReducer {
    override fun reduce(oldViewState: IViewState, lceResult: Lce<IResult>): IViewState {
        return viewStateUpdater
            .createByLce(lceResult).updateFromLceResult(oldViewState, lceResult)
    }
}