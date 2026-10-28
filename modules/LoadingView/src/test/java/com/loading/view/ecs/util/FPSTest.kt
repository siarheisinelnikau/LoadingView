package com.loading.view.ecs.util

import org.junit.Assert.assertEquals
import org.junit.Test

class FPSTest {
    private var now = 0L
    private val clock = FPS { now }

    @Test
    fun firstFrameDoesNotAdvanceSimulation() {
        now = 5_000_000_000L
        clock.onFrame()
        assertEquals(0f, clock.getDelta(), 0f)
    }

    @Test
    fun frameDeltaUsesSecondsAndCapsLongPauses() {
        clock.onFrame()
        now += 16_000_000L
        clock.onFrame()
        assertEquals(0.016f, clock.getDelta(), 0.000001f)
        now += 10_000_000_000L
        clock.onFrame()
        assertEquals(0.025f, clock.getDelta(), 0.000001f)
    }

    @Test
    fun resumeStartsWithoutAJump() {
        clock.onFrame()
        now += 16_000_000L
        clock.onFrame()
        clock.reset()
        now += 60_000_000_000L
        clock.onFrame()
        assertEquals(0f, clock.getDelta(), 0f)
    }

    @Test
    fun fpsUsesActualElapsedTime() {
        clock.onFrame()
        repeat(50) {
            now += 20_000_000L
            clock.onFrame()
        }
        assertEquals(50, clock.getFps())
    }
}
