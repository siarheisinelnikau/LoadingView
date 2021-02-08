package com.loading.view.space.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.loading.view.ecs.util.VectorFloat
import com.loading.view.space.component.MovementComponent
import com.loading.view.space.component.SizeComponent


class RotationSystem(
    private val smallSpeedMin: Float,
    private val smallSpeedMax: Float,
    private val bigSpeedMin: Float,
    private val bigSpeedMax: Float
) :
    IteratingSystem(family) {

    companion object {
        private val family = Family
            .all(
                SizeComponent::class.java,
                MovementComponent::class.java
            )
            .get()
    }

    var rotationRequested = false

    override fun update(deltaTime: Float) {
        if (rotationRequested) {
            super.update(deltaTime)
            rotationRequested = false
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val mc = MovementComponent.from(entity)
        val sc = SizeComponent.from(entity)

        if (sc.big) {
            mc.speed.x = VectorFloat.random360(bigSpeedMin, bigSpeedMax)
            mc.speed.y = VectorFloat.random360(bigSpeedMin, bigSpeedMax)
        } else {
            mc.speed.x = VectorFloat.random360(smallSpeedMin, smallSpeedMax)
            mc.speed.y = VectorFloat.random360(smallSpeedMin, smallSpeedMax)
        }
    }
}