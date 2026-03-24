package com.rainyday.momentapp.features.events.domain.usecases

import com.rainyday.momentapp.core.data.models.Result
import com.rainyday.momentapp.features.events.domain.models.Event
import com.rainyday.momentapp.features.events.domain.models.EventParamsRequest
import com.rainyday.momentapp.features.events.domain.repository.IEventsRemoteRepository
import javax.inject.Inject

class UpdateEventUseCase @Inject constructor(
    private val repository: IEventsRemoteRepository
) {
    suspend operator fun invoke(paramsReq: EventParamsRequest.UpdateEventParamsReq): Result<Event> {
        return repository.updateEvent(paramsReq)
    }
}