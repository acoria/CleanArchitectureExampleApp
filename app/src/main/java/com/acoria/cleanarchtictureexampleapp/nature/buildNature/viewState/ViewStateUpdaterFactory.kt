package com.acoria.cleanarchtictureexampleapp.nature.buildNature.viewState

import com.acoria.cleanarchtictureexampleapp.mvi.IResult
import com.acoria.cleanarchtictureexampleapp.mvi.IViewStateUpdater
import com.acoria.cleanarchtictureexampleapp.core.Lce
import com.acoria.cleanarchtictureexampleapp.mvi.IViewState
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.IViewStateUpdaterFactory
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.NatureStateFlow

class ViewStateUpdaterFactory:
    IViewStateUpdaterFactory<IViewState, Lce<IResult>> {

    override fun createByLce(lce: Lce<IResult>): IViewStateUpdater<IViewState, Lce<IResult>> {

        return when(lce) {
            is Lce.Loading -> {
                LoadingViewStateUpdater() as IViewStateUpdater<IViewState, Lce<IResult>>
            }

            is Lce.Content -> {
                ContentViewStateUpdater() as IViewStateUpdater<IViewState, Lce<IResult>>
            }

            is Lce.Error -> {
                ErrorViewStateUpdater() as IViewStateUpdater<IViewState, Lce<IResult>>
            }
        }
    }

}