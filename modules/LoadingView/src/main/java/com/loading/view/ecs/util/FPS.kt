package com.loading.view.ecs.util

import android.util.Log
import com.loading.view.BuildConfig
import kotlin.math.min

class FPS {

    companion object {
        const val SECOND_MS = 1000
    }

    private var time = 0L
    private var delta = 0f

    private var fps = 0
    private var frames = 0

    private var passedMs = 0L

    fun onFrame() {
        val oldTime = time
        val newTime = System.currentTimeMillis()
        time = newTime

        delta = min(0.025f, (newTime - oldTime) / 1000f) // min 40 fps expected


        if (frames == 0) {
            passedMs = System.currentTimeMillis()
        }

        ++frames

        if (System.currentTimeMillis() - passedMs > SECOND_MS) {
            fps = frames
            frames = 0

            if (BuildConfig.DEBUG) {
                Log.d(javaClass.simpleName, getFps().toString())
            }
        }
    }

    fun getDelta(): Float = delta

    fun getFps(): Int = fps
}