package com.loading.view.space.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.loading.view.ecs.util.VectorFloat
import com.loading.view.space.component.LineComponent
import com.loading.view.space.component.SizeComponent
import com.loading.view.space.component.TransformComponent

class LinesSystem(
    backgroundProximity: Float,
    foregroundProximity: Float
) : IteratingSystem(family) {
    companion object {
        private val family = Family.all(
            TransformComponent::class.java,
            SizeComponent::class.java,
            LineComponent::class.java
        ).get()
    }

    private val backgroundProximitySquared = backgroundProximity * backgroundProximity
    private val foregroundProximitySquared = foregroundProximity * foregroundProximity

    override fun update(deltaTime: Float) {
        val size = entities.size()
        for (i in 0 until size) {
            val entity = entities[i]
            val line = LineComponent.from(entity)
            line.active = false
            val foreground = SizeComponent.from(entity).big
            val position = TransformComponent.from(entity).position
            var nearestDistance = if (foreground) {
                foregroundProximitySquared
            } else {
                backgroundProximitySquared
            }

            // One outgoing connection per dot; only look forward to avoid duplicate edges.
            for (j in i + 1 until size) {
                val other = entities[j]
                if (SizeComponent.from(other).big != foreground) continue
                val otherPosition = TransformComponent.from(other).position
                val distance = VectorFloat.distanceSquared(position, otherPosition)
                if (distance < nearestDistance) {
                    nearestDistance = distance
                    line.position.set(otherPosition)
                    line.active = true
                }
            }
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) = Unit
}
