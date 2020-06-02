package com.acoria.cleanarchtictureexampleapp

import android.app.Activity

fun Activity.getViewModelFactory() : ViewModelFactory{
    val repository = (applicationContext as CustomApplication).plantRepository
    return ViewModelFactory(repository)
}