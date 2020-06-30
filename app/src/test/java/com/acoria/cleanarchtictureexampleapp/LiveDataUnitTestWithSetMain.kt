package com.acoria.cleanarchtictureexampleapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LiveDataUnitTestWithSetMain {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
//        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun liveDataTest() = runBlockingTest {
        val mutableLiveData = MutableLiveData<String>()
        mutableLiveData.postValue("test")
        assertEquals("test", mutableLiveData.value)
    }

    @After
    fun teardown() {
//        Dispatchers.resetMain()
    }

}