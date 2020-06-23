package com.acoria.cleanarchtictureexampleapp.nature

sealed class Lce<T> {
    data class Loading<T>(val loadingContent: T? = null) : Lce<T>()
    data class Content<T>(val content: T) : Lce<T>()
    data class Error<T>(val error: T) : Lce<T>()
}