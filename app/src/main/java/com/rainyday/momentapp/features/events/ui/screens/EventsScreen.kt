package com.rainyday.momentapp.features.events.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rainyday.momentapp.core.ui.components.EmptyContentState
import com.rainyday.momentapp.core.ui.components.ErrorState
import com.rainyday.momentapp.core.ui.components.LoadingContentState
import com.rainyday.momentapp.core.ui.theme.MomentAppTheme
import com.rainyday.momentapp.features.events.domain.models.Event
import com.rainyday.momentapp.features.events.ui.components.EventCard
import com.rainyday.momentapp.features.events.ui.intents.EventsListIntent
import com.rainyday.momentapp.features.events.ui.state.EventsSideEffect
import com.rainyday.momentapp.features.events.ui.state.EventsUiState
import com.rainyday.momentapp.features.events.ui.viewmodel.EventsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    eventsViewModel: EventsViewModel = hiltViewModel(),
    onAddEventScreenNavigate: () -> Unit,
    onEventDetailsScreenNavigate: (String) -> Unit,
) {
    val uiState by eventsViewModel.eventsUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        eventsViewModel.handleIntent(EventsListIntent.RefreshEvents)

        eventsViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is EventsSideEffect.ReloadEvents -> {
                    eventsViewModel.handleIntent(EventsListIntent.RefreshEvents)
                }
                else -> Unit
            }
        }
    }

    EventsScreenContent(
        uiState = uiState,
        onAddEventScreenNavigate,
        onEventDetailsScreenNavigate,
        onRefreshEvents = {
            eventsViewModel.handleIntent(EventsListIntent.RefreshEvents)
        }
    )
}

@Composable
fun EventsScreenContent(
    uiState: EventsUiState,
    onAddEventScreenNavigate: () -> Unit,
    onEventDetailsScreenNavigate: (String) -> Unit,
    onRefreshEvents: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (val state = uiState) {
            is EventsUiState.Loading -> LoadingContentState()
            is EventsUiState.Empty -> EmptyContentState()
            is EventsUiState.Refreshing -> LoadingContentState()
            is EventsUiState.Error -> ErrorState(state.message) {
                onRefreshEvents()
            }
            is EventsUiState.Success -> {
                EventsListContent(
                    events = uiState.events,
                    isRefreshing = state.isRefreshing,
                    onRefreshEvents = {
                        onRefreshEvents()
                    },
                    onEventClick = { event ->
                        onEventDetailsScreenNavigate(event.id)
                    }
                )

                FloatingActionButton(
                    onClick = {
                        onAddEventScreenNavigate()
                    },
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 20.dp, end = 20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Event"
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventsListContent(
    events: List<Event>,
    isRefreshing: Boolean,
    onRefreshEvents: () -> Unit,
    onEventClick: (Event) -> Unit
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefreshEvents,
    ) {
        LazyColumn (
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(events, key = { it.id!! }) { event ->
                EventCard(event = event, onClick = { onEventClick(event) }, highlight = true)
            }
        }
    }
}

@Preview
@Composable
fun PreviewEventsScreen() {
    MomentAppTheme {
        EventsScreenContent(
            uiState = EventsUiState.Success(
                events = listOf(
                    Event(
                        id = "0",
                        title = "Романтична вечеря",
                        description = "Чудовий вечір у затишному ресторані з італійською кухнею. Замовили пасту карбонара та тірамісу. Атмосфера була нейовірна",
                        date = 1773532800
                    ),
                    Event(
                        id = "1",
                        title = "Романтична вечеря",
                        description = "Чудовий вечір у затишному ресторані з італійською кухнею. Замовили пасту карбонара та тірамісу. Атмосфера була нейовірна",
                        date = 1773532800
                    ),
                    Event(
                        id = "2",
                        title = "Романтична вечеря",
                        description = "Чудовий вечір у затишному ресторані з італійською кухнею. Замовили пасту карбонара та тірамісу. Атмосфера була нейовірна",
                        date = 1773532800
                    )
                ),
                isRefreshing = false,
            ),
            onAddEventScreenNavigate = { },
            onEventDetailsScreenNavigate = { },
            onRefreshEvents = { }
        )
    }
}

@Preview
@Composable
fun PreviewEmptyEventsScreen() {
    MomentAppTheme {
        EventsScreenContent(
            uiState = EventsUiState.Empty,
            onAddEventScreenNavigate = { },
            onEventDetailsScreenNavigate = { },
            onRefreshEvents = { }
        )
    }
}

@Preview
@Composable
fun PreviewLoadingEventsScreen() {
    MomentAppTheme {
        EventsScreenContent(
            uiState = EventsUiState.Loading,
            onAddEventScreenNavigate = { },
            onEventDetailsScreenNavigate = { },
            onRefreshEvents = { }
        )
    }
}

@Preview
@Composable
fun PreviewErrorEventsScreen() {
    MomentAppTheme {
        EventsScreenContent(
            uiState = EventsUiState.Error(message = "Error 500: Internal Error"),
            onAddEventScreenNavigate = { },
            onEventDetailsScreenNavigate = { },
            onRefreshEvents = { }
        )
    }
}