package com.acoria.cleanarchtictureexampleapp.nature.buildNature

import com.acoria.cleanarchtictureexampleapp.mvi.IViewStateUpdater
import com.acoria.cleanarchtictureexampleapp.mvi.IResult
import com.acoria.cleanarchtictureexampleapp.mvi.IViewState
import com.acoria.cleanarchtictureexampleapp.core.Lce

interface IViewStateUpdaterFactory<S: IViewState, L: Lce<IResult>> {
    fun createByLce(lce: L): IViewStateUpdater<S, L>
}
