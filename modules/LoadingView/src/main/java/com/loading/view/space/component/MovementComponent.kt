package com.loading.view.space.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.loading.view.ecs.util.VectorFloat

class MovementComponent(val speed: VectorFloat) : Component {

    companion object {

        private val mapper: ComponentMapper<MovementComponent> =
            ComponentMapper.getFor(MovementComponent::class.java)

        fun from(entity: Entity): MovementComponent = mapper.get(entity)

    }


}