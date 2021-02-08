package com.loading.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.loading.view.ecs.util.asDp
import com.loading.view.space.Configuration
import com.loading.view.space.SpaceWorld


class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private val configuration: Configuration = Configuration.from(context, attrs)

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

    private var world: SpaceWorld? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        world = null
    }

    override fun onDraw(canvas: Canvas) {
        if (!isEnabled) {
            return
        }

        if (world == null) {
            world = makeWorld()
        }

        canvas.save()

        world?.apply {
            renderSystem.setCanvas(canvas)
            update()
        }

        canvas.restore()

        postInvalidate()
    }

    private fun makeWorld(): SpaceWorld {
        val w = width.toFloat()
        val h = height.toFloat()
        val wDp = w.asDp(context)
        val hDp = h.asDp(context)

        return SpaceWorld.create(w, h, wDp, hDp, configuration)
    }
}