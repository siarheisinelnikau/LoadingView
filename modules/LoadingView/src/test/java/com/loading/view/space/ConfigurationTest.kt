package com.loading.view.space

import android.app.Application
import com.loading.view.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23, 37], qualifiers = "xhdpi")
class ConfigurationTest {
    private val context: Application get() = RuntimeEnvironment.getApplication()

    @Test
    fun xmlDimensionsAreConvertedToPixelsExactlyOnce() {
        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.backgroundMinRadius, "2dp")
            .addAttribute(R.attr.backgroundMaxRadius, "2dp")
            .addAttribute(R.attr.foregroundMinSpeed, "3dp")
            .addAttribute(R.attr.foregroundMaxSpeed, "3dp")
            .addAttribute(R.attr.backgroundProximity, "10dp")
            .build()
        val config = Configuration.from(context, attrs)
        assertEquals(4f, config.backgroundMinRadius, 0.001f)
        assertEquals(4f, config.backgroundMaxRadius, 0.001f)
        assertEquals(6f, config.foregroundMinSpeed, 0.001f)
        assertEquals(20f, config.backgroundProximity, 0.001f)
    }

    @Test
    fun fallbackDimensionsUseTheSameDpScale() {
        val config = Configuration.from(context, null)
        assertEquals(2f, config.backgroundMaxRadius, 0.001f)
        assertEquals(120f, config.foregroundProximity, 0.001f)
    }

    @Test
    fun rejectsInvalidConfigurationBeforeWorldCreation() {
        val config = Configuration.from(context, null)
        assertThrows(IllegalArgumentException::class.java) { config.copy(backgroundDensity = 0) }
        assertThrows(IllegalArgumentException::class.java) { config.copy(foregroundLineAlpha = 256) }
        assertThrows(IllegalArgumentException::class.java) { config.copy(backgroundMinRadius = -1f) }
        assertThrows(IllegalArgumentException::class.java) { config.copy(foregroundMinSpeed = Float.NaN) }
        assertThrows(IllegalArgumentException::class.java) { config.copy(backgroundMaxSpeed = 0f) }
    }
}
