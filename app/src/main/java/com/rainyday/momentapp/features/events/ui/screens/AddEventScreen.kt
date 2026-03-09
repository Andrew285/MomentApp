package com.rainyday.momentapp.features.events.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.rainyday.momentapp.features.events.ui.viewmodel.AddEventViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    viewModel: AddEventViewModel = hiltViewModel(),
    onClose: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)
    ) {
        // Screen Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            Text(
                text = stringResource(R.string.new_event),
                modifier = Modifier
                    .weight(1f)
            )

            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = stringResource(R.string.close_add_event_screen_content_description),
                modifier = Modifier.clickable(onClick = { onClose() } )
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart)
        ) {
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
                    viewModel.updateTitle(newTitle)
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
                    viewModel.updateDescription(newDescription)
                }
            )

            // Date
            DateTimeComponent(
                title = stringResource(R.string.date),
                selectedDateInMilliseconds = uiState.dateTimeInMillis,
                onSelectedDate = { selectedMillis ->
                    viewModel.updateDate(selectedMillis)
                }
            )

            // Confirm button
            BasicButton(
                text = stringResource(R.string.create_event),
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    viewModel.createEvent()
                    onClose()
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewAddEventScreen() {
    MomentAppTheme {
        AddEventScreen(
            onClose = {}
        )
    }
}