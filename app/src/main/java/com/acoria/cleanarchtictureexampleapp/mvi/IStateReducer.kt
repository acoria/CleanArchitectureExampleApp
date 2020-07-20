package com.acoria.cleanarchtictureexampleapp.mvi

import com.acoria.cleanarchtictureexampleapp.core.Lce


interface IStateReducer {
    fun reduce(oldViewState: IViewState, lceResult: Lce<IResult>): IViewState
}