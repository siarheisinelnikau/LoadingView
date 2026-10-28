package com.loading.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.loading.view.ecs.util.asDp
import com.loading.view.space.Configuration
import com.loading.view.space.SpaceWorld

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {
    private val configuration = Configuration.from(context, attrs, defStyleAttr)
    private var world: SpaceWorld? = null
    private var attached = false

    private val shouldAnimate: Boolean
        get() = attached && isShown && windowVisibility == VISIBLE && isEnabled

    init {
        isClickable = true
        isFocusable = true
    }

    override fun performClick(): Boolean {
        super.performClick()
        world?.rotate()
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        world = makeWorld()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        attached = true
        if (world == null) world = makeWorld()
        updateAnimationState()
    }

    override fun onDetachedFromWindow() {
        attached = false
        world = null
        super.onDetachedFromWindow()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        updateAnimationState()
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        updateAnimationState()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        updateAnimationState()
    }

    private fun updateAnimationState() {
        world?.resetTiming()
        if (shouldAnimate) postInvalidateOnAnimation()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val animate = shouldAnimate
        val saveCount = canvas.save()
        try {
            world?.draw(canvas, animate)
        } finally {
            canvas.restoreToCount(saveCount)
        }
        if (animate) postInvalidateOnAnimation()
    }

    private fun makeWorld(): SpaceWorld? {
        if (width <= 0 || height <= 0) return null
        val w = width.toFloat()
        val h = height.toFloat()
        return SpaceWorld.create(w, h, w.asDp(context), h.asDp(context), configuration)
    }
}
