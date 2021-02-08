package com.loading.view.core.renderers

import android.graphics.Canvas
import android.graphics.Paint
import com.loading.view.core.Dot
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

    fun render(dots: List<Dot>, canvas: Canvas) = dots.forEach { particle ->
        with(particle) {
            val diff = maxRadius - minRadius
            val alpha = min(
                255f,
                ((radius - minRadius) / diff * alphaMultiplier).toInt() + minAlpha
            )
            paint.alpha = alpha.toInt()
            canvas.drawCircle(position.x, position.y, radius, paint)
        }
    }
}