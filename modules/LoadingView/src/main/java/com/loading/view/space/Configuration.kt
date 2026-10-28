package com.loading.view.space

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.loading.view.R
import com.loading.view.ecs.util.asPixel

/** Immutable settings. Dimensions and speeds are stored in pixels and pixels/second. */
data class Configuration(
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
    init {
        require(backgroundLineAlpha in 0..255 && foregroundLineAlpha in 0..255) {
            "Line alpha must be between 0 and 255"
        }
        require(backgroundDensity > 0 && foregroundDensity > 0) {
            "Layer density must be positive (area in dp squared per dot)"
        }
        require(validRange(backgroundMinSpeed, backgroundMaxSpeed) &&
            validRange(foregroundMinSpeed, foregroundMaxSpeed)) {
            "Speeds must be finite, nonnegative, and min must not exceed max"
        }
        require(validRange(backgroundMinRadius, backgroundMaxRadius) &&
            validRange(foregroundMinRadius, foregroundMaxRadius)) {
            "Radii must be finite, nonnegative, and min must not exceed max"
        }
        require(backgroundProximity.isFinite() && backgroundProximity >= 0f &&
            foregroundProximity.isFinite() && foregroundProximity >= 0f) {
            "Proximity must be finite and nonnegative"
        }
    }

    companion object {
        private fun validRange(min: Float, max: Float) =
            min.isFinite() && max.isFinite() && min >= 0f && max >= min

        private val default = Configuration(
            gradientStart = Color.parseColor("#FF022126"),
            gradientEnd = Color.parseColor("#FF3F3251"),
            backgroundLineAlpha = 16,
            backgroundMaxSpeed = 4f,
            backgroundMinSpeed = 2f,
            backgroundMaxRadius = 1f,
            backgroundMinRadius = 0.4f,
            backgroundDensity = 2000,
            backgroundProximity = 35f,
            foregroundLineAlpha = 48,
            foregroundMaxSpeed = 15f,
            foregroundMinSpeed = 6f,
            foregroundMaxRadius = 1.4f,
            foregroundMinRadius = 1.1f,
            foregroundDensity = 5000,
            foregroundProximity = 60f
        )

        fun from(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int = 0,
            defStyleRes: Int = 0
        ): Configuration {
            val values = context.obtainStyledAttributes(
                attrs, R.styleable.LoadingView, defStyleAttr, defStyleRes
            )
            try {
                // getDimension already returns pixels; only fallback dp values need conversion.
                return Configuration(
                    gradientStart = values.getColor(
                        R.styleable.LoadingView_gradientStart, default.gradientStart
                    ),
                    gradientEnd = values.getColor(
                        R.styleable.LoadingView_gradientEnd, default.gradientEnd
                    ),
                    backgroundLineAlpha = values.getInt(
                        R.styleable.LoadingView_backgroundLineAlpha, default.backgroundLineAlpha
                    ),
                    backgroundMaxSpeed = values.getDimension(
                        R.styleable.LoadingView_backgroundMaxSpeed, default.backgroundMaxSpeed.asPixel(context)
                    ),
                    backgroundMinSpeed = values.getDimension(
                        R.styleable.LoadingView_backgroundMinSpeed, default.backgroundMinSpeed.asPixel(context)
                    ),
                    backgroundMaxRadius = values.getDimension(
                        R.styleable.LoadingView_backgroundMaxRadius, default.backgroundMaxRadius.asPixel(context)
                    ),
                    backgroundMinRadius = values.getDimension(
                        R.styleable.LoadingView_backgroundMinRadius, default.backgroundMinRadius.asPixel(context)
                    ),
                    backgroundDensity = values.getInt(
                        R.styleable.LoadingView_backgroundDensity, default.backgroundDensity
                    ),
                    backgroundProximity = values.getDimension(
                        R.styleable.LoadingView_backgroundProximity, default.backgroundProximity.asPixel(context)
                    ),
                    foregroundLineAlpha = values.getInt(
                        R.styleable.LoadingView_foregroundLineAlpha, default.foregroundLineAlpha
                    ),
                    foregroundMaxSpeed = values.getDimension(
                        R.styleable.LoadingView_foregroundMaxSpeed, default.foregroundMaxSpeed.asPixel(context)
                    ),
                    foregroundMinSpeed = values.getDimension(
                        R.styleable.LoadingView_foregroundMinSpeed, default.foregroundMinSpeed.asPixel(context)
                    ),
                    foregroundMaxRadius = values.getDimension(
                        R.styleable.LoadingView_foregroundMaxRadius, default.foregroundMaxRadius.asPixel(context)
                    ),
                    foregroundMinRadius = values.getDimension(
                        R.styleable.LoadingView_foregroundMinRadius, default.foregroundMinRadius.asPixel(context)
                    ),
                    foregroundDensity = values.getInt(
                        R.styleable.LoadingView_foregroundDensity, default.foregroundDensity
                    ),
                    foregroundProximity = values.getDimension(
                        R.styleable.LoadingView_foregroundProximity, default.foregroundProximity.asPixel(context)
                    )
                )
            } finally {
                values.recycle()
            }
        }
    }
}
