package com.example.myapplication

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ResourceComparerTest{

    lateinit var resourceComparer:ResourceComparer
    lateinit var context: Context

    @Before
    fun setup(){
        resourceComparer = ResourceComparer()
        context =  ApplicationProvider.getApplicationContext()
    }

    @Test
    fun stringResourceSameAsStringGiven(){
        val result = resourceComparer.isEqual(context,R.string.app_name,"Bedu Shop")
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceDifferentAsStringGiven_returnsFalse(){
        val result = resourceComparer.isEqual(context,R.string.app_name,"Testing")
        assertThat(result).isFalse()
    }

}