package com.loading.view.space.component

import android.graphics.Canvas
import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity

class RenderComponent(val renderer: (Entity, Canvas) -> Unit) : Component {

    companion object {

        private val mapper: ComponentMapper<RenderComponent> =
            ComponentMapper.getFor(RenderComponent::class.java)

        fun from(entity: Entity): RenderComponent = mapper.get(entity)

    }

}