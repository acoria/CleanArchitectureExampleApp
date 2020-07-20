package com.acoria.cleanarchtictureexampleapp

import com.acoria.cleanarchtictureexampleapp.core.Lce
import com.acoria.cleanarchtictureexampleapp.mvi.IResult
import com.acoria.cleanarchtictureexampleapp.nature.buildNature.viewState.ViewStateUpdaterFactory
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito

class ViewStateUpdaterFactoryUnitTest {

    @Test
    fun createByLce(){
        assertNotNull(ViewStateUpdaterFactory().createByLce(Lce.Loading(Mockito.mock(IResult::class.java))))
    }
}