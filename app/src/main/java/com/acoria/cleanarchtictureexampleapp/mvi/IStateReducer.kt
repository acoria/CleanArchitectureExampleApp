package com.acoria.cleanarchtictureexampleapp.mvi

import com.acoria.cleanarchtictureexampleapp.nature.Lce
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.NatureResult


interface IStateReducer<S: IViewState, R: IResult> {
    fun reduce(oldIViewState: S, result: Lce<R>): S
}