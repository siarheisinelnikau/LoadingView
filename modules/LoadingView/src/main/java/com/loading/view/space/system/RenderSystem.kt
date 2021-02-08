package com.loading.view.space.system

import android.graphics.Canvas
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.loading.view.space.component.RenderComponent

class RenderSystem : IteratingSystem(family) {

    companion object {
        private val family = Family.all(RenderComponent::class.java).get()
    }

    private lateinit var canvas: Canvas

    fun setCanvas(canvas: Canvas) {
        this.canvas = canvas
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        RenderComponent.from(entity).renderer(entity, canvas)
    }
}