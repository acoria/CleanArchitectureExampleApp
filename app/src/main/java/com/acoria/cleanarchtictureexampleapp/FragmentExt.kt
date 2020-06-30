package com.acoria.cleanarchtictureexampleapp

import androidx.fragment.app.Fragment

fun Fragment.getViewModelFactory() : ViewModelFactory{
    val repository = (requireContext().applicationContext as CustomApplication).plantRepository
    val dispatcherProvider = (requireContext().applicationContext as CustomApplication).dispatcherProvider
    return ViewModelFactory(repository, dispatcherProvider, this)
}