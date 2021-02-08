package com.loading.view.core.util

class Pool<T>(private val creator: () -> T) {

    private val list = ArrayList<T>(128)

    fun obtain(): T {
        if (list.isEmpty()) {
            return creator()
        }

        return list.removeLast()
    }

    fun reset() = list.clear()

    fun free(vararg objects: T) {
        list.addAll(objects)
    }

    fun free(objects: Collection<T>) {
        list.addAll(objects)
    }


}