package com.rainyday.momentapp.features.events.ui.state

sealed class EventsSideEffect{
    data object EventAddedSuccessfully: EventsSideEffect()
    data object EventAddedFailed: EventsSideEffect()
    data class UnknownError(val message: String): EventsSideEffect()
    data object ReloadEvents: EventsSideEffect()
}