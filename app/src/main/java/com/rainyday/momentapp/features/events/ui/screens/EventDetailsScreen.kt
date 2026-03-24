package com.rainyday.momentapp.features.events.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.size.Size
import com.rainyday.momentapp.R
import com.rainyday.momentapp.core.ui.components.EmptyContentState
import com.rainyday.momentapp.core.ui.components.ErrorButton
import com.rainyday.momentapp.core.ui.components.LoadingContentState
import com.rainyday.momentapp.core.ui.theme.MomentAppTheme
import com.rainyday.momentapp.features.events.domain.models.Event
import com.rainyday.momentapp.features.events.domain.models.EventParamsRequest
import com.rainyday.momentapp.features.events.ui.intents.EventsListIntent
import com.rainyday.momentapp.features.events.ui.state.EventDetailsUiState
import com.rainyday.momentapp.features.events.ui.state.EventsSideEffect
import com.rainyday.momentapp.features.events.ui.viewmodel.EventDetailsViewModel
import com.rainyday.momentapp.features.events.ui.viewmodel.EventsViewModel

@Composable
fun EventDetailsScreen(
    eventId: String,
    viewModel: EventDetailsViewModel = hiltViewModel(),
    eventsViewModel: EventsViewModel = hiltViewModel(),
    onEdit: (String) -> Unit,
    onClose: () -> Unit,
    showSnackBar: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val deleteEventSuccessfullyString = stringResource(R.string.snack_bar_event_delete_success)
    val deleteEventFailedString = stringResource(R.string.snack_bar_event_delete_fail)

    LaunchedEffect(Unit) {
        viewModel.getEventDetails(eventId)

        eventsViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                EventsSideEffect.EventDeletedSuccessfully -> {
                    showSnackBar(deleteEventSuccessfullyString)
                    onClose()
                }
                EventsSideEffect.EventDeletedFailed -> {
                    showSnackBar(deleteEventFailedString)
                }
                else -> Unit
            }
        }
    }

    when (val state = uiState) {
        is EventDetailsUiState.Empty -> EmptyContentState()
        is EventDetailsUiState.Loading -> LoadingContentState()
        is EventDetailsUiState.Error -> { showSnackBar(state.message) }
        is EventDetailsUiState.Success -> {
            EventDetailsScreenContent(
                state.event,
                onEdit = {
                    onEdit(eventId)
                },
                onDelete = { id ->
                    eventsViewModel.handleIntent(EventsListIntent.DeleteEvent(id))
                },
                onClose = onClose
            )
        }
    }
}

@Composable
fun EventDetailsScreenContent(
    event: Event,
    onEdit: (EventParamsRequest.UpdateEventParamsReq) -> Unit,
    onDelete: (String) -> Unit,
    onClose: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        MainImage(
            id = event.id,
            title = event.title,
            onEdit = {
                val params = EventParamsRequest.UpdateEventParamsReq(
                    id = event.id,
                    title = event.title,
                    description = event.description,
                    date = event.date
                )
                onEdit(params)
            },
            onClose = onClose
        )

        Spacer(modifier = Modifier.height(10.dp))

        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 20.dp)
        )

        DescriptionContentBlock(
            description = event.description
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 20.dp)
        )

        ErrorButton(
            text = stringResource(R.string.delete),
            onClick = {
                onDelete(event.id)
            },
            modifier = Modifier
                .width(150.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun MainImage(
    id: String,
    title: String,
    onEdit: () -> Unit,
    onClose: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        if (true) {
            Image(
                painter = painterResource(R.drawable.gradient_background),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(0.6f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
        }
        else {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.gradient_background)
                    .size(Size.ORIGINAL) // or specific size
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(0.6f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                    bottom = 10.dp,
                    top = 10.dp,
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedIconButton(
                    shape = CircleShape,
                    colors = IconButtonDefaults.iconButtonColors().copy(
                        containerColor = Color(0xFF1c0e1c)
                    ),
                    onClick = onEdit,
                    modifier = Modifier
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit event",
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                OutlinedIconButton(
                    shape = CircleShape,
                    colors = IconButtonDefaults.iconButtonColors().copy(
                        containerColor = Color(0xFF1c0e1c)
                    ),
                    onClick = {
                        onClose()
                    },
                    modifier = Modifier
                        .size(40.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Close event details",
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }

            // Title
            Text(
                text = title,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
fun DescriptionContentBlock(
    description: String,
) {
    ContentBlock(
        icon = {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = "About event icon"
            )
        },
        title = stringResource(R.string.about_event)
    ) {
        Text(
            text = description,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
fun ContentBlock(
    title: String,
    icon: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            icon()

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        content()
    }
}

@Preview
@Composable
fun PreviewContentBlock() {
    MomentAppTheme {
        ContentBlock(
            icon = {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = "About event icon"
                )
            },
            title = "Про подію"
        ) {
            Text(
                text = "Чудовий вечір у затишному закладі з італійською кухнею. Замовили пасту карбонара та тірамісу. Атмосфера була неймовірна!",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Preview
@Composable
fun PreviewEventDetailsScreenContent() {
    MomentAppTheme {
        EventDetailsScreenContent(
            event = Event(
                id = "",
                title = "Романтична вечеря в Доброму друзі",
                description = "Чудовий вечір у затишному закладі з італійською кухнею. Замовили пасту карбонара та тірамісу. Атмосфера була неймовірна!",
                date = 1773532800
            ),
            onEdit = {},
            onDelete = {},
            onClose = {}
        )
    }
}