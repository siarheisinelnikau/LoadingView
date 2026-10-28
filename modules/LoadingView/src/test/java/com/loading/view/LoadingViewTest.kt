package com.loading.view

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Looper
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23, 37])
class LoadingViewTest {
    @Test
    fun touchAndAccessibilityClicksUseTheSameClickAction() {
        val controller = Robolectric.buildActivity(Activity::class.java).setup()
        try {
            val view = LoadingView(controller.get())
            controller.get().setContentView(view)
            view.layout(0, 0, 100, 100)
            view.requestFocus()
            var clicks = 0
            view.setOnClickListener { clicks++ }
            view.performClick()
            val now = SystemClock.uptimeMillis()
            val down = MotionEvent.obtain(now, now, MotionEvent.ACTION_DOWN, 1f, 1f, 0)
            val up = MotionEvent.obtain(now, now + 10, MotionEvent.ACTION_UP, 1f, 1f, 0)
            try {
                assertTrue(view.dispatchTouchEvent(down))
                assertTrue(view.dispatchTouchEvent(up))
                shadowOf(Looper.getMainLooper()).idleFor(32, TimeUnit.MILLISECONDS)
            } finally {
                down.recycle()
                up.recycle()
            }
            assertEquals(2, clicks)
        } finally {
            controller.pause().stop().destroy()
        }
    }

    @Test
    @Config(sdk = [37]) // Frame callback invalidation is observable in the modern View shadow.
    fun disabledViewStopsRequestingAnimationFrames() {
        val controller = Robolectric.buildActivity(Activity::class.java).setup()
        try {
            val view = LoadingView(controller.get())
            controller.get().setContentView(view)
            view.layout(0, 0, 100, 100)
            view.isEnabled = false
            val looper = shadowOf(Looper.getMainLooper())
            looper.idleFor(64, TimeUnit.MILLISECONDS)
            val shadow = shadowOf(view)
            shadow.clearWasInvalidated()
            draw(view)
            looper.idleFor(32, TimeUnit.MILLISECONDS)
            assertFalse(shadow.wasInvalidated())
            view.isEnabled = true
            looper.idleFor(32, TimeUnit.MILLISECONDS)
            assertTrue(shadow.wasInvalidated())
        } finally {
            controller.pause().stop().destroy()
        }
    }

    @Test
    fun narrowViewWithParticlesCanRender() {
        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.backgroundDensity, "1")
            .addAttribute(R.attr.foregroundDensity, "1")
            .addAttribute(R.attr.backgroundMinRadius, "1dp")
            .addAttribute(R.attr.backgroundMaxRadius, "1dp")
            .build()
        val view = LoadingView(RuntimeEnvironment.getApplication(), attrs)
        view.layout(0, 0, 1, 10)
        val bitmap = Bitmap.createBitmap(1, 10, Bitmap.Config.ARGB_8888)
        try {
            view.draw(Canvas(bitmap))
        } finally {
            bitmap.recycle()
        }
    }

    @Test
    fun supportsResizeDisableHideAndReattach() {
        val controller = Robolectric.buildActivity(Activity::class.java).setup()
        try {
            val activity = controller.get()
            val view = LoadingView(activity)
            activity.setContentView(view)
            assertTrue(view.isAttachedToWindow)
            view.layout(0, 0, 100, 100)
            draw(view)
            view.isEnabled = false
            draw(view)
            view.visibility = View.INVISIBLE
            view.visibility = View.VISIBLE
            view.isEnabled = true
            view.layout(0, 0, 200, 100)
            draw(view)
            activity.setContentView(View(activity))
            assertFalse(view.isAttachedToWindow)
            activity.setContentView(view)
            assertTrue(view.isAttachedToWindow)
            draw(view)
        } finally {
            controller.pause().stop().destroy()
        }
    }

    private fun draw(view: View) {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        try {
            view.draw(Canvas(bitmap))
        } finally {
            bitmap.recycle()
        }
    }
}
