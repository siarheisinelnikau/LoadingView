package com.loading.view.space.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity

class SizeComponent(val radius: Float, val big: Boolean) : Component {

    companion object {

        private val mapper: ComponentMapper<SizeComponent> =
            ComponentMapper.getFor(SizeComponent::class.java)

        fun from(entity: Entity): SizeComponent = mapper.get(entity)

    }

}