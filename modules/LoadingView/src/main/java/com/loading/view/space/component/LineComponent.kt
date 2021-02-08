package com.loading.view.space.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.loading.view.ecs.util.VectorFloat

class LineComponent(val position: VectorFloat, var active: Boolean = false) : Component {

    companion object {

        private val mapper: ComponentMapper<LineComponent> =
            ComponentMapper.getFor(LineComponent::class.java)

        fun from(entity: Entity): LineComponent = mapper.get(entity)

    }
}