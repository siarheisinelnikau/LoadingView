package com.loading.view.core.renderers

import android.graphics.Canvas
import android.graphics.Paint
import com.loading.view.core.Line

class LineRenderer(alpha: Int) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.style = Paint.Style.FILL
        it.setARGB(alpha, 255, 255, 255)
    }

    fun render(lines: List<Line>, canvas: Canvas) = lines.forEach {
        canvas.drawLine(
            it.begin.x,
            it.begin.y,
            it.end.x,
            it.end.y,
            paint
        )
    }
}