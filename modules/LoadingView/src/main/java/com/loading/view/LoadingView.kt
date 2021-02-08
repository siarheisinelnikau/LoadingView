package com.loading.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.loading.view.core.*
import com.loading.view.core.renderers.DotRenderer
import com.loading.view.core.renderers.GradientRenderer
import com.loading.view.core.renderers.LineRenderer
import com.loading.view.core.systems.World
import com.loading.view.core.util.asDp


class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private val configuration: Configuration = Configuration.from(context, attrs)

    private val backgroundLinesRenderer = LineRenderer(configuration.backgroundLineAlpha)
    private val backgroundDotsRenderer = DotRenderer(
        configuration.backgroundMaxRadius,
        configuration.backgroundMinRadius,
        170f,
        50f
    ) // TODO hardcoded values

    private val foregroundLinesRenderer = LineRenderer(configuration.foregroundLineAlpha)
    private val foregroundDotsRenderer = DotRenderer(
        configuration.foregroundMaxRadius,
        configuration.foregroundMinRadius,
        128f,
        128f
    ) // TODO hardcoded values

    private val gradientRenderer by lazy {
        GradientRenderer(
            width.toFloat(),
            height.toFloat(),
            configuration.gradientStart,
            configuration.gradientEnd
        )
    }

    private val touchListener = OnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                world?.apply { rotate() }
                performClick()
                true
            }
            else -> {
                false
            }
        }
    }

    init {
        isClickable = true
        isFocusable = true
        setOnTouchListener(touchListener)
    }

    private var world: World? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        world = null
    }

    override fun onDraw(canvas: Canvas) {
        if (!isEnabled) return

        if (world == null) world = makeWorld()

        canvas.save()

        world?.let {
            it.update()

            gradientRenderer.render(canvas)

            backgroundLinesRenderer.render(it.getBackgroundLines(), canvas)
            backgroundDotsRenderer.render(it.getBackgroundDots(), canvas)

            foregroundLinesRenderer.render(it.getForegroundLines(), canvas)
            foregroundDotsRenderer.render(it.getForegroundDots(), canvas)
        }

        canvas.restore()

        postInvalidate()
    }

    private fun makeWorld(): World {
        val w = width.toFloat()
        val h = height.toFloat()
        val wDp = w.asDp(context)
        val hDp = h.asDp(context)

        return World(w, h, wDp, hDp, configuration)
    }
}