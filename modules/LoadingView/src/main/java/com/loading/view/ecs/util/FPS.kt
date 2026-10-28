package com.loading.view.ecs.util

/** Monotonic animation clock. Long pauses never become large simulation steps. */
class FPS(private val nanoTime: () -> Long = System::nanoTime) {
    private var previousTime = 0L
    private var hasFrame = false
    private var intervalStart = 0L
    private var frames = 0
    private var delta = 0f
    private var fps = 0

    fun onFrame() {
        val now = nanoTime()
        val previous = previousTime
        previousTime = now
        if (!hasFrame) {
            hasFrame = true
            intervalStart = now
            delta = 0f
            return
        }

        delta = ((now - previous) / 1_000_000_000f).coerceIn(0f, 0.025f)
        frames++
        val elapsed = now - intervalStart
        if (elapsed >= 1_000_000_000L) {
            fps = (frames * 1_000_000_000.0 / elapsed).toInt()
            frames = 0
            intervalStart = now
        }
    }

    fun reset() {
        hasFrame = false
        frames = 0
        delta = 0f
        fps = 0
    }

    fun getDelta(): Float = delta
    fun getFps(): Int = fps
}
