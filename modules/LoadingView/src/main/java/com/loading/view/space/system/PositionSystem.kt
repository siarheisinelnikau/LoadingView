package com.loading.view.space.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.loading.view.ecs.util.VectorFloat
import com.loading.view.space.component.MovementComponent
import com.loading.view.space.component.TransformComponent


class PositionSystem(
    private val width: Float,
    private val height: Float
) :
    IteratingSystem(family) {

    companion object {
        private val family = Family
            .all(
                TransformComponent::class.java,
                MovementComponent::class.java
            )
            .get()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val tc = TransformComponent.from(entity)
        val mc = MovementComponent.from(entity)

        tc.position.x += mc.speed.x * deltaTime
        tc.position.y += mc.speed.y * deltaTime

        handleBounds(tc.position, mc.speed)
    }

    private fun handleBounds(position: VectorFloat, speed: VectorFloat) {
        when {
            position.x < 0f -> {
                position.x = 0f
                speed.x = -speed.x
            }

            position.x > width -> {
                position.x = width
                speed.x = -speed.x
            }

            position.y < 0f -> {
                position.y = 0f
                speed.y = -speed.y
            }

            position.y > height -> {
                position.y = height
                speed.y = -speed.y
            }
        }
    }
}