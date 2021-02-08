package com.loading.view.core.systems

import com.loading.view.core.Dot
import com.loading.view.core.util.VectorFloat
import kotlin.random.Random

class DotsSystem(
    private val width: Float,
    private val height: Float,
    widthDp: Float,
    heightDp: Float,
    density: Int,
    val minRadius: Float,
    val maxRadius: Float,
    private val minSpeed: Float,
    private val maxSpeed: Float
) {

    private val count = (widthDp * heightDp / density).toInt()
    private val particles: MutableList<Dot> = ArrayList(count)

    fun getParticles(): List<Dot> = particles

    fun update(delta: Float) {
        if (particles.size != count) {
            fillParticles()
        }

        particles.forEach { particle ->
            particle.position.x += particle.speed.x * delta
            particle.position.y += particle.speed.y * delta

            handleBounds()
        }
    }

    fun rotate() = particles.forEach { particle ->
        particle.speed.x = VectorFloat.random360(minSpeed, maxSpeed)
        particle.speed.y = VectorFloat.random360(minSpeed, maxSpeed)
    }

    private fun fillParticles() {
        for (i: Int in 0 until count) {
            val dot = Dot(
                VectorFloat.random(minRadius, maxRadius),
                VectorFloat(0f, 0f),
                VectorFloat(
                    VectorFloat.random360(minSpeed, maxSpeed),
                    VectorFloat.random360(minSpeed, maxSpeed)
                )
            )

            particles += dot
        }

        for (particle in particles) {
            particle.position.x = Random.nextInt(0, width.toInt()).toFloat()
            particle.position.y = Random.nextInt(0, height.toInt()).toFloat()
        }
    }

    private fun handleBounds() = particles.forEach { particle ->
        with(particle) {
            when {
                position.x < 0f -> {
                    position.x = 0f
                    speed.x = -speed.x
                }

                position.x > width -> {
                    position.x = width
                    speed.x = -speed.x
                }

                position.y < 0f -> {
                    position.y = 0f
                    speed.y = -speed.y
                }

                position.y > height -> {
                    position.y = height
                    speed.y = -speed.y
                }
            }
        }
    }
}