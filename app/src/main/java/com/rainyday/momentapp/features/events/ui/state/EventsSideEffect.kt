package com.rainyday.momentapp.features.events.ui.state

sealed class EventsSideEffect{
    data object EventAddedSuccessfully: EventsSideEffect()
    data object EventUpdatedSuccessfully: EventsSideEffect()
    data object EventDeletedSuccessfully: EventsSideEffect()
    data object EventAddedFailed: EventsSideEffect()
    data object EventUpdatedFailed: EventsSideEffect()
    data object EventDeletedFailed: EventsSideEffect()
    data class UnknownError(val message: String): EventsSideEffect()
    data object ReloadEvents: EventsSideEffect()
}