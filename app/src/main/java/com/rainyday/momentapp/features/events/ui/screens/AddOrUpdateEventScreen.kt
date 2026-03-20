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
import com.rainyday.momentapp.features.events.domain.models.EventParamsRequest
import com.rainyday.momentapp.features.events.ui.intents.EventsListIntent
import com.rainyday.momentapp.features.events.ui.state.AddOrUpdateEventUiState
import com.rainyday.momentapp.features.events.ui.state.EventsSideEffect
import com.rainyday.momentapp.features.events.ui.viewmodel.EventsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrUpdateEventScreen(
    eventId: String? = null,
    viewModel: EventsViewModel = hiltViewModel(),
    onClose: () -> Unit,
    showSnackBar: (String) -> Unit,
) {
    val uiState by viewModel.addOrUpdateEventUiState.collectAsStateWithLifecycle()
    val eventAddedSuccessfullyString = stringResource(R.string.snack_bar_event_create_success)
    val eventUpdatedSuccessfullyString = stringResource(R.string.snack_bar_event_update_success)
    val eventDeletedSuccessfullyString = stringResource(R.string.snack_bar_event_delete_success)
    val eventAddedFailedString = stringResource(R.string.snack_bar_event_create_fail)
    val eventUpdatedFailedString = stringResource(R.string.snack_bar_event_update_fail)
    val eventDeletedFailedString = stringResource(R.string.snack_bar_event_delete_fail)
    val unknownErrorString = stringResource(R.string.snack_bar_unknown_error)

    LaunchedEffect(Unit) {
        // preload event data before editing
        eventId?.let {
            viewModel.handleIntent(EventsListIntent.PreLoadEventBeforeEditing(it))
        }

        // collect side effects
        viewModel.sideEffects.collect { effect ->
            when (effect) {
                EventsSideEffect.EventAddedSuccessfully -> {
                    showSnackBar(eventAddedSuccessfullyString)
                    onClose()
                }
                EventsSideEffect.EventUpdatedSuccessfully -> {
                    showSnackBar(eventUpdatedSuccessfullyString)
                    onClose()
                }
                EventsSideEffect.EventDeletedSuccessfully -> {
                    showSnackBar(eventDeletedSuccessfullyString)
                    onClose()
                }
                EventsSideEffect.EventAddedFailed -> {
                    showSnackBar(eventAddedFailedString)
                }
                EventsSideEffect.EventUpdatedFailed -> {
                    showSnackBar(eventUpdatedFailedString)
                }
                EventsSideEffect.EventDeletedFailed -> {
                    showSnackBar(eventDeletedFailedString)
                }
                is EventsSideEffect.UnknownError -> {
                    showSnackBar(unknownErrorString)
                }
                is EventsSideEffect.ReloadEvents -> Unit
            }
        }
    }


    AddOrUpdateScreenContent(
        uiState = uiState,
        isEditing = eventId != null,
        onTitleUpdated = { newTitle ->
            viewModel.handleIntent(EventsListIntent.UpdateTitle(newTitle))
        },
        onDescriptionUpdated = { newDescription ->
            viewModel.handleIntent(EventsListIntent.UpdateDescription(newDescription))
        },
        onDateUpdated = { selectedMillis ->
            viewModel.handleIntent(EventsListIntent.UpdateDate(selectedMillis))
        },
        onSaveEvent = { params ->
            viewModel.handleIntent(EventsListIntent.SaveEvent(params))
        },
        onClose = onClose
    )
}

@Composable
fun AddOrUpdateScreenContent(
    uiState: AddOrUpdateEventUiState,
    isEditing: Boolean,
    onTitleUpdated: (String) -> Unit,
    onDescriptionUpdated: (String) -> Unit,
    onDateUpdated: (Long) -> Unit,
    onSaveEvent: (EventParamsRequest) -> Unit,
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
                    text = stringResource(if (!isEditing) R.string.new_event else R.string.edit_event),
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
                    val params = if (isEditing) {
                        EventParamsRequest.UpdateEventParamsReq(
                            id = uiState.id,
                            title = uiState.title,
                            description = uiState.description,
                            date = uiState.dateTimeInMillis,
                        )
                    }
                    else {
                        EventParamsRequest.CreateEventParamsReq(
                            title = uiState.title,
                            description = uiState.description,
                            date = uiState.dateTimeInMillis,
                        )
                    }
                    onSaveEvent(params)
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewAddEventScreen() {
    MomentAppTheme {
        AddOrUpdateScreenContent(
            uiState = AddOrUpdateEventUiState(),
            isEditing = false,
            onTitleUpdated = {},
            onDescriptionUpdated = {},
            onDateUpdated = {},
            onSaveEvent = {},
            onClose = {}
        )
    }
}