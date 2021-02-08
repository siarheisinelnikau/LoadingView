package com.loading.view.space.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.loading.view.ecs.util.VectorFloat

class TransformComponent(val position: VectorFloat) : Component {

    companion object {

        private val mapper: ComponentMapper<TransformComponent> =
            ComponentMapper.getFor(TransformComponent::class.java)

        fun from(entity: Entity): TransformComponent = mapper.get(entity)

    }

}