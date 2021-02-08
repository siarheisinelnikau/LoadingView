@file:Suppress("NOTHING_TO_INLINE")

package com.loading.view.core.util

import kotlin.random.Random

/**
 * Vector<T>, but optimized for usage without boxing/unboxing
 */
class VectorFloat(var x: Float, var y: Float) {

    companion object {

        inline fun random(from: Float, to: Float): Float =
            (to - from) * Random.nextFloat() + from

        inline fun random360(from: Float, to: Float): Float =
            random(from, to) * if (Random.nextBoolean()) 1f else -1f

        inline fun distanceSquared(p1: VectorFloat, p2: VectorFloat) =
            (p2.y - p1.y) * (p2.y - p1.y) + (p2.x - p1.x) * (p2.x - p1.x)

    }

    constructor() : this(0f, 0f)
}