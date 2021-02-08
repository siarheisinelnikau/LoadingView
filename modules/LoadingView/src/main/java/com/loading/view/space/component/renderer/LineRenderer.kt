package com.loading.view.space.component.renderer

import android.graphics.Canvas
import android.graphics.Paint
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.loading.view.space.component.LineComponent
import com.loading.view.space.component.TransformComponent

class LineRenderer(alpha: Int) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.style = Paint.Style.FILL
        it.setARGB(alpha, 255, 255, 255)
    }

    private val tm: ComponentMapper<TransformComponent> =
        ComponentMapper.getFor(TransformComponent::class.java)

    private val lm: ComponentMapper<LineComponent> =
        ComponentMapper.getFor(LineComponent::class.java)

    fun render(entity: Entity, canvas: Canvas) {
        val lc = lm.get(entity)

        if (lc.active) {
            val tc = tm.get(entity)

            canvas.drawLine(
                tc.position.x,
                tc.position.y,
                lc.position.x,
                lc.position.y,
                paint
            )
        }
    }
}