package com.loading.view.ecs.util

import android.content.Context
import android.util.DisplayMetrics

fun Float.asPixel(context: Context) =
    this * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

fun Float.asDp(context: Context) =
    this / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)