package com.loading.view.space

import com.badlogic.ashley.core.Engine
import com.loading.view.ecs.util.FPS
import com.loading.view.ecs.util.VectorFloat
import com.loading.view.space.component.*
import com.loading.view.space.component.renderer.DotRenderer
import com.loading.view.space.component.renderer.GradientRenderer
import com.loading.view.space.component.renderer.LineRenderer
import com.loading.view.space.system.LinesSystem
import com.loading.view.space.system.PositionSystem
import com.loading.view.space.system.RenderSystem
import com.loading.view.space.system.RotationSystem
import kotlin.random.Random

class SpaceWorld private constructor(private val engine: Engine) {

    companion object {

        fun create(
            width: Float,
            height: Float,
            widthDp: Float,
            heightDp: Float,
            configuration: Configuration
        ): SpaceWorld {
            val engine = Engine()

            // Fill with systems

            engine.addSystem(PositionSystem(width, height))

            engine.addSystem(LinesSystem(configuration.backgroundProximity))
            engine.addSystem(LinesSystem(configuration.foregroundProximity))

            engine.addSystem(
                RotationSystem(
                    configuration.backgroundMinSpeed,
                    configuration.backgroundMaxSpeed,
                    configuration.foregroundMinSpeed,
                    configuration.foregroundMaxSpeed
                )
            )

            engine.addSystem(RenderSystem())

            // make renderers

            // TODO hardcoded values
            val bgDotRenderer = DotRenderer(
                configuration.backgroundMaxRadius,
                configuration.backgroundMinRadius,
                170f,
                50f
            )

            val bgLineRenderer = LineRenderer(
                configuration.backgroundLineAlpha
            )

            // TODO hardcoded values
            val fgDotRenderer = DotRenderer(
                configuration.foregroundMaxRadius,
                configuration.foregroundMinRadius,
                128f,
                128f
            )

            val fgLineRenderer = LineRenderer(
                configuration.foregroundLineAlpha
            )

            val gradientRenderer = GradientRenderer(
                width,
                height,
                configuration.gradientStart,
                configuration.gradientEnd
            )


            // Fill with entities

            val entity = engine.createEntity()
            entity.add(RenderComponent(gradientRenderer::render))
            engine.addEntity(entity)

            engine.initializeLayer(
                width,
                height,
                widthDp,
                heightDp,
                configuration.backgroundDensity,
                configuration.backgroundMinRadius,
                configuration.backgroundMaxRadius,
                configuration.backgroundMinSpeed,
                configuration.backgroundMaxSpeed,
                false,
                renderer = {
                    RenderComponent { entity, canvas ->
                        bgLineRenderer.render(entity, canvas)
                        bgDotRenderer.render(entity, canvas)
                    }
                }
            )

            engine.initializeLayer(
                width,
                height,
                widthDp,
                heightDp,
                configuration.foregroundDensity,
                configuration.foregroundMinRadius,
                configuration.foregroundMaxRadius,
                configuration.foregroundMinSpeed,
                configuration.foregroundMaxSpeed,
                true,
                renderer = {
                    RenderComponent { entity, canvas ->
                        fgLineRenderer.render(entity, canvas)
                        fgDotRenderer.render(entity, canvas)
                    }
                }
            )

            return SpaceWorld(engine)
        }

        private fun Engine.initializeLayer(
            width: Float,
            height: Float,
            widthDp: Float,
            heightDp: Float,
            density: Int,
            minRadius: Float,
            maxRadius: Float,
            minSpeed: Float,
            maxSpeed: Float,
            foreground: Boolean,
            renderer: () -> RenderComponent
        ) {
            val count = (widthDp * heightDp / density).toInt()

            (0 until count).forEach { _ ->
                val entity = createEntity()

                entity.add(
                    MovementComponent(
                        VectorFloat(
                            VectorFloat.random360(minSpeed, maxSpeed),
                            VectorFloat.random360(minSpeed, maxSpeed)
                        )
                    )
                )

                entity.add(
                    SizeComponent(VectorFloat.random(minRadius, maxRadius), foreground)
                )

                entity.add(
                    TransformComponent(
                        VectorFloat(
                            Random.nextInt(1, width.toInt() - 1).toFloat(),
                            Random.nextInt(1, height.toInt() - 1).toFloat()
                        )
                    )
                )

                entity.add(LineComponent(VectorFloat()))

                entity.add(renderer.invoke())

                addEntity(entity)
            }
        }

    }

    private val fps = FPS()

    private val rotationSystem: RotationSystem get() = engine.getSystem(RotationSystem::class.java)
    val renderSystem: RenderSystem get() = engine.getSystem(RenderSystem::class.java)

    fun update() {
        fps.onFrame()
        val delta = fps.getDelta()

        engine.update(delta)
    }

    fun rotate() {
        rotationSystem.rotationRequested = true
    }


}