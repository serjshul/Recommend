package com.serj.recommend.android

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    fun getContext(): Context {
        val context = InstrumentationRegistry
            .getInstrumentation()
            .targetContext

        return context
    }

    @Test
    fun checkPackageName() {
        assertEquals(
            "com.serj.recommend.android",
            getContext().packageName
        )
    }

    @Test
    fun checkWrongPackageName() {
        assertNotEquals(
            "com.serj.recomme1111nd.android",
            getContext().packageName
        )
    }
}