package com.rainyday.momentapp.features.events.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.Uri
import com.rainyday.momentapp.core.ui.screens.BaseScreen
import com.rainyday.momentapp.features.events.domain.models.Event
import com.rainyday.momentapp.features.events.ui.components.TopAppBarTitleComponent
import com.rainyday.momentapp.features.events.ui.viewmodel.EventsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxScope.EventsScreen(
    eventsViewModel: EventsViewModel = hiltViewModel(),
    onAddEventScreenNavigate: () -> Unit,
) {
    val uiState = eventsViewModel.eventsUiState.collectAsStateWithLifecycle()

    when {
        uiState.value.isLoading && uiState.value.events.isEmpty() ->
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        uiState.value.error != null && uiState.value.events.isEmpty() -> {}
//                    ErrorContent(uiState.error!!) {
//                        viewModel.onEvent(EventsListUiEvent.Refresh)
//                    }
        uiState.value.events.isEmpty() -> { }
//                    EmptyContent { viewModel.navigateToAddEvent() }
        else -> {
            EventsListContent(
                events = uiState.value.events,
                onEventClick = { /* viewModel.navigateToDetail(it.id) */ }
            )

            FloatingActionButton(
                onClick = {
                    onAddEventScreenNavigate()
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Event"
                )
            }
        }
    }

//    BaseScreen(
//        mainSmallImage = "",
//        navController = navController
//    ) { padding ->
//        Box(Modifier.fillMaxSize().padding(padding)) {
//
//        }
//    }
}


//@Composable
//private fun ErrorContent() {
//    Box(
//        modifier = Modifier
//            .
//    )
//}

@Composable
private fun EventsListContent(
    events: List<Event>,
    onEventClick: (Event) -> Unit
) {
    // Групуємо за статусом
//    val todayEvents = events.filter { it.isToday }
//    val upcomingEvents = events.filter { it.isUpcoming && !it.isToday }
//    val pastEvents = events.filter { !it.isUpcoming && !it.isToday }

    LazyColumn (
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(events, key = { it.id }) { event ->
            EventCard(event = event, onClick = { onEventClick(event) }, highlight = true)
        }

//        if (todayEvents.isNotEmpty()) {
//            item { Text("🎉  Today") }
//            items(todayEvents, key = { it.id }) { event ->
//                EventCard(event = event, onClick = { onEventClick(event) }, highlight = true)
//            }
//        }
//        if (upcomingEvents.isNotEmpty()) {
//            item { Text("📅  Upcoming") }
//            items(upcomingEvents, key = { it.id }) { event ->
//                EventCard(event = event, onClick = { onEventClick(event) })
//            }
//        }
//        if (pastEvents.isNotEmpty()) {
//            item { Text("🕰️  Past") }
//            items(pastEvents, key = { it.id }) { event ->
//                EventCard(event = event, onClick = { onEventClick(event) }, dimmed = true)
//            }
//        }
//        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun EventCard(event: Event, onClick: () -> Unit,
              highlight: Boolean = false, dimmed: Boolean = false) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().alpha(if (dimmed) 0.6f else 1f),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(if (highlight) 8.dp else 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (highlight) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Column {

            Text(
                text = event.title
            )

            Text(
                text = event.description
            )

//            // Cover image або gradient placeholder
//            if (event.coverImageUrl != null) {
//                AsyncImage(model = event.coverImageUrl, contentDescription = null,
//                    modifier = Modifier.fillMaxWidth().height(140.dp),
//                    contentScale = ContentScale.Crop)
//            } else {
//                Box(
//                    Modifier.fillMaxWidth().height(80.dp).background(
//                        Brush.horizontalGradient(listOf(
//                            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
//                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)
//                        ))
//                    )
//                )
//            }
//            Column(Modifier.padding(16.dp)) {
//                Row(Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically) {
//                    Text(event.date.formatToDisplay(),
//                        style = MaterialTheme.typography.labelMedium,
//                        color = MaterialTheme.colorScheme.primary)
//                    if (event.isToday) {
//                        Surface(shape = RoundedCornerShape(8.dp),
//                            color = MaterialTheme.colorScheme.primary) {
//                            Text("TODAY", Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
//                                style = MaterialTheme.typography.labelSmall,
//                                color = MaterialTheme.colorScheme.onPrimary)
//                        }
//                    }
//                }
//                Spacer(Modifier.height(4.dp))
//                Text(event.title, style = MaterialTheme.typography.titleMedium,
//                    fontWeight = FontWeight.SemiBold, maxLines = 2,
//                    overflow = TextOverflow.Ellipsis)
//                Spacer(Modifier.height(4.dp))
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(Icons.Default.LocationOn, null, Modifier.size(14.dp),
//                        tint = MaterialTheme.colorScheme.onSurfaceVariant)
//                    Spacer(Modifier.width(4.dp))
//                    Text(event.location, style = MaterialTheme.typography.bodySmall,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                        maxLines = 1, overflow = TextOverflow.Ellipsis)
//                }
//                if (event.hasMultipleParticipants) {
//                    Spacer(Modifier.height(6.dp))
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Icon(Icons.Default.Group, null, Modifier.size(14.dp),
//                            tint = MaterialTheme.colorScheme.onSurfaceVariant)
//                        Spacer(Modifier.width(4.dp))
//                        Text("${event.participantIds.size} participants",
//                            style = MaterialTheme.typography.bodySmall,
//                            color = MaterialTheme.colorScheme.onSurfaceVariant)
//                    }
//                }
//            }
        }
    }
}