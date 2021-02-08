package com.loading.view.core.systems

import com.loading.view.core.Dot
import com.loading.view.core.Line
import com.loading.view.core.util.Pool
import com.loading.view.core.util.VectorFloat

class LinesSystem(
    private val proximity: Float,
    private val pool: Pool<Line>,
    private val dotsProvider: () -> List<Dot>
) {
    private val lines = ArrayList<Line>()

    fun getLines(): List<Line> = lines

    fun update() {
        pool.reset()
        pool.free(lines)
        lines.clear()

        val particles = dotsProvider()

        // distanceSquared * distanceSquared = (y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)

        // passes count = N*N/2
        // 11111
        // 01111
        // 00111
        // 00011
        for (i: Int in 0 until particles.size - 1) {
            for (e: Int in i + 1 until particles.size) {
                checkLine(particles[i].position, particles[e].position, proximity)
            }
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun checkLine(p1: VectorFloat, p2: VectorFloat, proximity: Float) {
        if (proximity * proximity > VectorFloat.distanceSquared(p1, p2)) {
            val line = pool.obtain()
            line.begin = p1
            line.end = p2
            lines += line
        }
    }
}