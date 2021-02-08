package com.loading.view.space.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.loading.view.ecs.util.VectorFloat
import com.loading.view.space.component.LineComponent
import com.loading.view.space.component.SizeComponent
import com.loading.view.space.component.TransformComponent

class LinesSystem(private val proximity: Float) : IteratingSystem(family) {

    companion object {

        private val family = Family
            .all(
                TransformComponent::class.java,
                SizeComponent::class.java,
                LineComponent::class.java
            )
            .get()

    }

    override fun update(deltaTime: Float) {
        // super.update ignored

        val size = entities.size()
        for (i: Int in 0 until size) {
            LineComponent.from(entities[i]).active = false
        }

        for (i: Int in 0 until size - 1) {
            for (e: Int in i + 1 until size) {
                checkLine(entities[i], entities[e], proximity)
            }
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun checkLine(a: Entity, b: Entity, proximity: Float) {
        val p1 = TransformComponent.from(a).position
        val p2 = TransformComponent.from(b).position

        val aType = SizeComponent.from(a).big
        val bType = SizeComponent.from(b).big

        if (aType == bType) {

            val line = LineComponent.from(a)

            if (VectorFloat.distanceSquared(p1, p2) < proximity * proximity) {
                line.position.set(p2)
                line.active = true
            }
        }
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        // empty, not used
    }
}