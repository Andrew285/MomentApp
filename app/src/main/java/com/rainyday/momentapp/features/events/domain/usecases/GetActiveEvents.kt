package com.rainyday.momentapp.features.events.domain.usecases

import com.rainyday.momentapp.core.data.models.Result
import com.rainyday.momentapp.features.events.domain.models.Event
import com.rainyday.momentapp.features.events.domain.repository.IEventsRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActiveEvents @Inject constructor(
    private val repository: IEventsRemoteRepository
) {
    operator fun invoke(): Flow<Result<List<Event>>> {
        return repository.getEvents()
    }
}