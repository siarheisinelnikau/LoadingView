package com.loading.view.ecs.util

import android.content.Context

fun Float.asPixel(context: Context) =
    this * context.resources.displayMetrics.density

fun Float.asDp(context: Context) =
    this / context.resources.displayMetrics.density
