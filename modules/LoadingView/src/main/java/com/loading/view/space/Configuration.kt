package com.loading.view.space

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.loading.view.R
import com.loading.view.ecs.util.asPixel

class Configuration(
    val gradientStart: Int,
    val gradientEnd: Int,
    val backgroundLineAlpha: Int,
    val backgroundMaxSpeed: Float,
    val backgroundMinSpeed: Float,
    val backgroundMaxRadius: Float,
    val backgroundMinRadius: Float,
    val backgroundDensity: Int,
    val backgroundProximity: Float,
    val foregroundLineAlpha: Int,
    val foregroundMaxSpeed: Float,
    val foregroundMinSpeed: Float,
    val foregroundMaxRadius: Float,
    val foregroundMinRadius: Float,
    val foregroundDensity: Int,
    val foregroundProximity: Float
) {

    companion object {
        private val default = Configuration(
            gradientStart = Color.parseColor("#FF022126"),
            gradientEnd = Color.parseColor("#FF3F3251"),
            backgroundLineAlpha = 16, // max 256

            backgroundMaxSpeed = 4.0f, //dp
            backgroundMinSpeed = 2.0f, //dp

            backgroundMaxRadius = 1.0f, //dp
            backgroundMinRadius = 0.4f, //dp

            backgroundDensity = 2000,
            backgroundProximity = 35f, //dp

            foregroundLineAlpha = 48, // max 256

            foregroundMaxSpeed = 15.0f, //dp
            foregroundMinSpeed = 6.0f, //dp

            foregroundMaxRadius = 1.4f, //dp
            foregroundMinRadius = 1.1f, //dp

            foregroundDensity = 5000,
            foregroundProximity = 60f, //dp
        )

        fun from(context: Context, attrs: AttributeSet?): Configuration {
            val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.LoadingView, 0, 0)

            val configuration = Configuration(
                gradientStart = typedArray.getColor(
                    R.styleable.LoadingView_gradientStart,
                    default.gradientStart
                ),
                gradientEnd = typedArray.getColor(
                    R.styleable.LoadingView_gradientEnd,
                    default.gradientEnd
                ),
                backgroundLineAlpha = typedArray.getInt(
                    R.styleable.LoadingView_backgroundLineAlpha,
                    default.backgroundLineAlpha
                ),
                backgroundMaxSpeed = typedArray.getDimension(
                    R.styleable.LoadingView_backgroundMaxSpeed,
                    default.backgroundMaxSpeed
                ).asPixel(context),
                backgroundMinSpeed = typedArray.getDimension(
                    R.styleable.LoadingView_backgroundMinSpeed,
                    default.backgroundMinSpeed
                ).asPixel(context),
                backgroundMaxRadius = typedArray.getDimension(
                    R.styleable.LoadingView_backgroundMaxRadius,
                    default.backgroundMaxRadius
                ).asPixel(context),
                backgroundMinRadius = typedArray.getDimension(
                    R.styleable.LoadingView_backgroundMinRadius,
                    default.backgroundMinRadius
                ).asPixel(context),
                backgroundDensity = typedArray.getInt(
                    R.styleable.LoadingView_backgroundDensity,
                    default.backgroundDensity
                ),
                backgroundProximity = typedArray.getDimension(
                    R.styleable.LoadingView_backgroundProximity,
                    default.backgroundProximity
                ).asPixel(context),
                foregroundLineAlpha = typedArray.getInt(
                    R.styleable.LoadingView_foregroundLineAlpha,
                    default.foregroundLineAlpha
                ),
                foregroundMaxSpeed = typedArray.getDimension(
                    R.styleable.LoadingView_foregroundMaxSpeed,
                    default.foregroundMaxSpeed
                ).asPixel(context),
                foregroundMinSpeed = typedArray.getDimension(
                    R.styleable.LoadingView_foregroundMinSpeed,
                    default.foregroundMinSpeed
                ).asPixel(context),
                foregroundMaxRadius = typedArray.getDimension(
                    R.styleable.LoadingView_foregroundMaxRadius,
                    default.foregroundMaxRadius
                ).asPixel(context),
                foregroundMinRadius = typedArray.getDimension(
                    R.styleable.LoadingView_foregroundMinRadius,
                    default.foregroundMinRadius
                ).asPixel(context),
                foregroundDensity = typedArray.getInt(
                    R.styleable.LoadingView_foregroundDensity,
                    default.foregroundDensity
                ),
                foregroundProximity = typedArray.getDimension(
                    R.styleable.LoadingView_foregroundProximity,
                    default.foregroundProximity
                ).asPixel(context)
            )

            typedArray.recycle()

            return configuration
        }
    }


}