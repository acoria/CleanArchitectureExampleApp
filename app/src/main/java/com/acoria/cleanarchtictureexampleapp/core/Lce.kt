package com.acoria.cleanarchtictureexampleapp.core

sealed class Lce<out T> {
    data class Loading<T>(val loadingContent: T? = null) : Lce<T>()
    data class Content<T>(val content: T) : Lce<T>()
    data class Error<T>(val error: T) : Lce<T>()
}