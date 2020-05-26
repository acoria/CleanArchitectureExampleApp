package com.acoria.cleanarchtictureexampleapp.nature.model

data class Plant(override val name: String, override val maxHeight: Int,
                 override val imageUrl: String?) : IPlant