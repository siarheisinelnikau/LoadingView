package com.loading.view.space.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.loading.view.ecs.util.VectorFloat
import com.loading.view.space.component.LineComponent
import com.loading.view.space.component.SizeComponent
import com.loading.view.space.component.TransformComponent
import org.junit.Assert.*
import org.junit.Test

class LinesSystemTest {
    private val engine = Engine().apply { addSystem(LinesSystem(10f, 30f)) }

    @Test
    fun layersHaveIndependentProximity() {
        val background = dot(0f, false)
        dot(20f, false)
        val foreground = dot(0f, true)
        dot(20f, true)
        engine.update(0f)
        assertFalse(LineComponent.from(background).active)
        assertTrue(LineComponent.from(foreground).active)
    }

    @Test
    fun neverConnectsDifferentLayers() {
        val background = dot(0f, false)
        val foreground = dot(1f, true)
        engine.update(0f)
        assertFalse(LineComponent.from(background).active)
        assertFalse(LineComponent.from(foreground).active)
    }

    @Test
    fun keepsNearestConnectionAndClearsItWhenDotsMoveApart() {
        val start = dot(0f, false)
        val nearest = dot(2f, false)
        val farther = dot(8f, false)
        engine.update(0f)
        val line = LineComponent.from(start)
        assertTrue(line.active)
        assertEquals(2f, line.position.x, 0f)
        TransformComponent.from(nearest).position.x = 100f
        TransformComponent.from(farther).position.x = 200f
        engine.update(0f)
        assertFalse(line.active)
    }

    @Test
    fun emptyAndSingleDotWorldsHaveNoConnections() {
        engine.update(0f)
        val onlyDot = dot(0f, false)
        engine.update(0f)
        assertFalse(LineComponent.from(onlyDot).active)
    }

    private fun dot(x: Float, foreground: Boolean) = Entity()
        .add(TransformComponent(VectorFloat(x, 0f)))
        .add(SizeComponent(1f, foreground))
        .add(LineComponent(VectorFloat()))
        .also { engine.addEntity(it) }
}
