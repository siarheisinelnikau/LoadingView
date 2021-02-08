package com.loading.view.space.component.renderer

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import com.badlogic.ashley.core.Entity

class GradientRenderer(
    width: Float,
    height: Float,
    gradientStart: Int,
    gradientEnd: Int
) {

    private val gradient =
        GradientDrawable(
            GradientDrawable.Orientation.BL_TR, intArrayOf(gradientStart, gradientEnd)
        )
            .apply {
                cornerRadius = 0f
                bounds = Rect(0, 0, width.toInt(), height.toInt())
            }

    fun render(entity: Entity, canvas: Canvas) {
        gradient.draw(canvas)
    }
}