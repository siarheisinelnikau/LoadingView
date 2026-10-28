package com.loading.view.space.system

import android.graphics.Canvas
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.loading.view.space.component.RenderComponent

class RenderSystem : IteratingSystem(Family.all(RenderComponent::class.java).get()) {
    private var canvas: Canvas? = null

    fun withCanvas(canvas: Canvas, update: () -> Unit) {
        this.canvas = canvas
        try {
            update()
        } finally {
            // A Canvas belongs to this draw call, not to the lifetime of the world.
            this.canvas = null
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        canvas?.let { RenderComponent.from(entity).renderer(entity, it) }
    }
}
