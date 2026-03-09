package com.rainyday.momentapp.features.events.domain.usecases

import com.rainyday.momentapp.core.data.models.Result
import com.rainyday.momentapp.features.events.domain.models.Event
import com.rainyday.momentapp.features.events.domain.repository.IEventsRemoteRepository
import javax.inject.Inject

class CreateEventUseCase @Inject constructor(
    private val eventsRepository: IEventsRemoteRepository
) {
    suspend operator fun invoke(event: Event): Result<Event> {
        return eventsRepository.createEvent(event)
    }
}