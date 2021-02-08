package com.loading.view.space.component.renderer

import android.graphics.Canvas
import android.graphics.Paint
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.loading.view.space.component.SizeComponent
import com.loading.view.space.component.TransformComponent
import kotlin.math.min


class DotRenderer(
    private val maxRadius: Float,
    private val minRadius: Float,
    private val alphaMultiplier: Float,
    private val minAlpha: Float
) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.style = Paint.Style.FILL
        it.setARGB(255, 255, 255, 255)
    }

    private val tm: ComponentMapper<TransformComponent> =
        ComponentMapper.getFor(TransformComponent::class.java)

    private val sm: ComponentMapper<SizeComponent> =
        ComponentMapper.getFor(SizeComponent::class.java)

    fun render(entity: Entity, canvas: Canvas) {
        val tc = tm.get(entity)
        val sc = sm.get(entity)

        val diff = maxRadius - minRadius
        val alpha = min(
            255f,
            ((sc.radius - minRadius) / diff * alphaMultiplier).toInt() + minAlpha
        )

        paint.alpha = alpha.toInt()

        if (sc.big) {
            paint.setARGB(alpha.toInt(), 255, 255, 0)
        } else {
            paint.setARGB(alpha.toInt(), 0, 255, 255)
        }
        canvas.drawCircle(tc.position.x, tc.position.y, sc.radius, paint)
    }
}