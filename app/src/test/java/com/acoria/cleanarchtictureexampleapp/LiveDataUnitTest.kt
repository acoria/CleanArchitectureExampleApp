package com.acoria.cleanarchtictureexampleapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class LiveDataUnitTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun liveDataTest() {
        val mutableLiveData = MutableLiveData<String>()
//        mutableLiveData.value = "test"
        mutableLiveData.postValue("test")
        assertEquals("test", mutableLiveData.value)
    }

}