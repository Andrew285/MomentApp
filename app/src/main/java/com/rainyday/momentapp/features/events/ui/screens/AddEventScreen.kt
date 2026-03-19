package com.rainyday.momentapp.features.events.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rainyday.momentapp.R
import com.rainyday.momentapp.core.ui.components.BasicButton
import com.rainyday.momentapp.core.ui.components.DateTimeComponent
import com.rainyday.momentapp.core.ui.components.MultiLineTextFieldComponent
import com.rainyday.momentapp.core.ui.components.TextFieldComponent
import com.rainyday.momentapp.core.ui.theme.MomentAppTheme
import com.rainyday.momentapp.features.events.domain.models.Event
import com.rainyday.momentapp.features.events.ui.intents.EventsListIntent
import com.rainyday.momentapp.features.events.ui.state.AddEventUiState
import com.rainyday.momentapp.features.events.ui.state.EventsSideEffect
import com.rainyday.momentapp.features.events.ui.viewmodel.EventsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    viewModel: EventsViewModel = hiltViewModel(),
    onClose: () -> Unit,
    showSnackBar: (String) -> Unit,
) {
    val uiState by viewModel.addEventUiState.collectAsStateWithLifecycle()
    val eventAddedSuccessfullyString = stringResource(R.string.snack_bar_event_create_success)
    val eventAddedFailedString = stringResource(R.string.snack_bar_event_create_fail)
    val unknownErrorString = stringResource(R.string.snack_bar_unknown_error)

    LaunchedEffect(Unit) {
        viewModel.sideEffects.collect { effect ->
            when (effect) {
                EventsSideEffect.EventAddedSuccessfully -> {
                    showSnackBar(eventAddedSuccessfullyString)
                    onClose()
                }
                EventsSideEffect.EventAddedFailed -> {
                    showSnackBar(eventAddedFailedString)
                }
                is EventsSideEffect.UnknownError -> {
                    showSnackBar(unknownErrorString)
                }
                is EventsSideEffect.ReloadEvents -> Unit
            }
        }
    }

    AddScreenContent(
        uiState = uiState,
        onTitleUpdated = { newTitle ->
            viewModel.handleIntent(EventsListIntent.UpdateTitle(newTitle))
        },
        onDescriptionUpdated = { newDescription ->
            viewModel.handleIntent(EventsListIntent.UpdateDescription(newDescription))
        },
        onDateUpdated = { selectedMillis ->
            viewModel.handleIntent(EventsListIntent.UpdateDate(selectedMillis))
        },
        onAddEvent = { event ->
            viewModel.handleIntent(EventsListIntent.AddEvent(event))
        },
        onClose = onClose
    )
}

@Composable
fun AddScreenContent(
    uiState: AddEventUiState,
    onTitleUpdated: (String) -> Unit,
    onDescriptionUpdated: (String) -> Unit,
    onDateUpdated: (Long) -> Unit,
    onAddEvent: (Event) -> Unit,
    onClose: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart)
        ) {
            // Screen Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp, horizontal = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.new_event),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.close_add_event_screen_content_description),
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable(onClick = { onClose() } )
                )
            }

            // Title
            TextFieldComponent(
                title = stringResource(R.string.event_name),
                textFieldValue = uiState.title,
                placeholder = {
                    Text(
                        text = stringResource(R.string.event_title_placeholder),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                onValueChange = { newTitle ->
                    onTitleUpdated(newTitle)
                }
            )

            // Description
            MultiLineTextFieldComponent(
                title = stringResource(R.string.description),
                textFieldValue = uiState.description,
                placeholder = {
                    Text(
                        text = stringResource(R.string.event_description_placeholder),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                onValueChange = { newDescription ->
                    onDescriptionUpdated(newDescription)
                }
            )

            // Date
            DateTimeComponent(
                title = stringResource(R.string.date),
                selectedDateInMilliseconds = uiState.dateTimeInMillis,
                onSelectedDate = { selectedMillis ->
                    onDateUpdated(selectedMillis)
                }
            )

            // Confirm button
            BasicButton(
                text = stringResource(R.string.create_event),
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    val event = Event(
                        title = uiState.title,
                        description = uiState.description,
                        date = uiState.dateTimeInMillis,
                    )
                    onAddEvent(event)
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewAddEventScreen() {
    MomentAppTheme {
        AddScreenContent(
            uiState = AddEventUiState(),
            onTitleUpdated = {},
            onDescriptionUpdated = {},
            onDateUpdated = {},
            onAddEvent = {},
            onClose = {}
        )
    }
}