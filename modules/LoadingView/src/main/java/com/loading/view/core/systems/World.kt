package com.loading.view.core.systems

import com.loading.view.core.Configuration
import com.loading.view.core.Dot
import com.loading.view.core.Line
import com.loading.view.core.util.FPS
import com.loading.view.core.util.Pool
import com.loading.view.core.util.VectorFloat

class World(
    width: Float,
    height: Float,
    widthDp: Float,
    heightDp: Float,
    configuration: Configuration
) {

    private val fps = FPS()

    fun update() {
        fps.onFrame()
        val delta = fps.getDelta()

        fgDots.update(delta)
        bgDots.update(delta)

        fgLines.update()
        bgLines.update()
    }

    fun rotate() {
        fgDots.rotate()
        bgDots.rotate()
    }

    fun getBackgroundLines(): List<Line> = bgLines.getLines()
    fun getBackgroundDots(): List<Dot> = bgDots.getParticles()

    fun getForegroundLines(): List<Line> = fgLines.getLines()
    fun getForegroundDots(): List<Dot> = fgDots.getParticles()

    private val fgDots = DotsSystem(
        width = width,
        height = height,
        widthDp = widthDp,
        heightDp = heightDp,
        density = configuration.foregroundDensity,
        maxRadius = configuration.foregroundMaxRadius,
        minRadius = configuration.foregroundMinRadius,
        maxSpeed = configuration.foregroundMaxSpeed,
        minSpeed = configuration.foregroundMinSpeed
    )

    private val bgDots = DotsSystem(
        width = width,
        height = height,
        widthDp = widthDp,
        heightDp = heightDp,
        density = configuration.backgroundDensity,
        maxRadius = configuration.backgroundMaxRadius,
        minRadius = configuration.backgroundMinRadius,
        maxSpeed = configuration.backgroundMaxSpeed,
        minSpeed = configuration.backgroundMinSpeed
    )

    private val fgLines = LinesSystem(
        proximity = configuration.foregroundProximity,
        pool = Pool { Line(VectorFloat(), VectorFloat()) },
        dotsProvider = fgDots::getParticles
    )


    private val bgLines = LinesSystem(
        proximity = configuration.backgroundProximity,
        pool = Pool { Line(VectorFloat(), VectorFloat()) },
        dotsProvider = bgDots::getParticles
    )
}