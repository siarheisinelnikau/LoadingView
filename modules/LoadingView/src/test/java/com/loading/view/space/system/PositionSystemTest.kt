package com.loading.view.space.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.loading.view.ecs.util.VectorFloat
import com.loading.view.space.component.MovementComponent
import com.loading.view.space.component.TransformComponent
import org.junit.Assert.assertEquals
import org.junit.Test

class PositionSystemTest {
    @Test
    fun movesUsingElapsedSeconds() {
        val entity = dot(20f, 20f, 8f, -4f)
        update(entity, 0.5f)
        assertPositionAndSpeed(entity, 24f, 18f, 8f, -4f)
    }

    @Test
    fun bouncesBothCoordinatesAtBottomRightCorner() {
        val entity = dot(99f, 79f, 10f, 10f)
        update(entity, 0.2f)
        assertPositionAndSpeed(entity, 100f, 80f, -10f, -10f)
    }

    @Test
    fun bouncesBothCoordinatesAtTopLeftCorner() {
        val entity = dot(1f, 1f, -10f, -10f)
        update(entity, 0.2f)
        assertPositionAndSpeed(entity, 0f, 0f, 10f, 10f)
    }

    private fun update(entity: Entity, delta: Float) {
        val engine = Engine()
        engine.addSystem(PositionSystem(100f, 80f))
        engine.addEntity(entity)
        engine.update(delta)
    }

    private fun dot(x: Float, y: Float, vx: Float, vy: Float) = Entity()
        .add(TransformComponent(VectorFloat(x, y)))
        .add(MovementComponent(VectorFloat(vx, vy)))

    private fun assertPositionAndSpeed(entity: Entity, x: Float, y: Float, vx: Float, vy: Float) {
        val position = TransformComponent.from(entity).position
        val speed = MovementComponent.from(entity).speed
        assertEquals(x, position.x, 0.00001f)
        assertEquals(y, position.y, 0.00001f)
        assertEquals(vx, speed.x, 0.00001f)
        assertEquals(vy, speed.y, 0.00001f)
    }
}
