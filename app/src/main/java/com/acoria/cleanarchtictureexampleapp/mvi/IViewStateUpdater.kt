package com.acoria.cleanarchtictureexampleapp.mvi

import com.acoria.cleanarchtictureexampleapp.core.Lce

interface IViewStateUpdater<S: IViewState, L: Lce<IResult>>{
    fun updateFromLceResult(oldViewState: S, lceResult: L): S
}