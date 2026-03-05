package com.rainyday.momentapp.features.events.data.mapper

import com.rainyday.momentapp.features.events.data.models.EventResponse
import com.rainyday.momentapp.features.events.domain.models.Event

fun EventResponse.toDomain() = Event(
    id = this.id,
    title = this.title,
    description = this.description
)