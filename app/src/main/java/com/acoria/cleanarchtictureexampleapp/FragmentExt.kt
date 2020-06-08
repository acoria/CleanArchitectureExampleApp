package com.acoria.cleanarchtictureexampleapp

import androidx.fragment.app.Fragment

fun Fragment.getViewModelFactory() : ViewModelFactory{
    val repository = (requireContext().applicationContext as CustomApplication).plantRepository
    return ViewModelFactory(repository, this)
}