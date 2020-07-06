package com.acoria.cleanarchtictureexampleapp.nature

import com.acoria.cleanarchtictureexampleapp.nature.model.IPlant
import com.acoria.cleanarchtictureexampleapp.nature.model.Plant
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlantRepository(private val drawableHelper: Map<String, Int>) : IPlantRepository {

    private val _stateFlow = MutableStateFlow<Int>(0)
    private var isFakeUpdateActive = true
    private val coroutineScope = CoroutineScope(newSingleThreadContext("some thread"))
    private var job: Job? = null

    init {
        fakeUpdate()
    }

    override suspend fun searchForPlant(plantName: String): IPlant? {
        //TODO - proper repository and more, such as DTOs
        var id = when (plantName) {
            PLANT_NAME_SUNFLOWER -> 1
            PLANT_NAME_PALMTREE -> 2
            else -> 0
        }
        var foundPlant: IPlant? = null
        val height = when (plantName) {
            PLANT_NAME_SUNFLOWER -> 2
            PLANT_NAME_PALMTREE -> 20
            else -> 0
        }
        if (height != 0) {
            foundPlant = Plant(id, plantName, height, drawableHelper[plantName].toString())
        }
        delay(2000)
        return foundPlant
    }

    override fun plantRequestCounterFlow(): StateFlow<Int> {
        return _stateFlow
    }

    fun stopUpdate() {
        coroutineScope.launch {
            // Cancel running job
            job?.let {
                it.cancel()
                it.join()
            }
        }
    }

    private fun fakeUpdate() {
        job = coroutineScope.launch {
            var counter = 0
            while (isFakeUpdateActive) {
                _stateFlow.value = counter++
                delay(3000)
            }
        }
    }
}