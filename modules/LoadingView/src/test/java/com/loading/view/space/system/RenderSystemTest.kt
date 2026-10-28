package com.loading.view.space.system

import android.graphics.Canvas
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.loading.view.space.component.RenderComponent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23, 37])
class RenderSystemTest {
    @Test
    fun neverReusesCanvasAfterDrawCallReturns() {
        val engine = Engine()
        val renderer = RenderSystem()
        engine.addSystem(renderer)
        var draws = 0
        engine.addEntity(Entity().add(RenderComponent { _, _ -> draws++ }))
        renderer.withCanvas(Canvas()) { renderer.update(0f) }
        renderer.update(0f)
        assertEquals(1, draws)
    }

    @Test
    fun releasesCanvasEvenWhenDrawingThrows() {
        val engine = Engine()
        val renderer = RenderSystem()
        engine.addSystem(renderer)
        var draws = 0
        engine.addEntity(Entity().add(RenderComponent { _, _ ->
            draws++
            throw IllegalStateException("Drawing failed")
        }))
        assertThrows(IllegalStateException::class.java) {
            renderer.withCanvas(Canvas()) { renderer.update(0f) }
        }
        renderer.update(0f)
        assertEquals(1, draws)
    }
}
