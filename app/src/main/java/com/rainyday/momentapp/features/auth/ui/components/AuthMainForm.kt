package com.rainyday.momentapp.features.auth.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rainyday.momentapp.R
import com.rainyday.momentapp.core.ui.components.BasicButton
import com.rainyday.momentapp.core.ui.components.TextFieldComponent
import com.rainyday.momentapp.core.ui.theme.MomentAppTheme

@Composable
fun AuthMainForm(
    onEmailTextChanged: (String) -> Unit,
    onPasswordTextChanged: (String) -> Unit,
    confirmBtnText: String,
    onConfirmBtn: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .wrapContentSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Email
            TextFieldComponent(
                title = stringResource(R.string.email),
                textFieldValue = "",
                placeholder = {
                    Text(
                        text = stringResource(R.string.email_placeholder),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                onValueChange = { newValue ->
                    onEmailTextChanged(newValue)
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Password
            TextFieldComponent(
                title = stringResource(R.string.password),
                textFieldValue = "",
                placeholder = {
                    Text(
                        text = stringResource(R.string.password_placeholder),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                onValueChange = { newValue ->
                    onPasswordTextChanged(newValue)
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Confirm Button
            BasicButton(
                text = confirmBtnText,
                onClick = onConfirmBtn,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview
@Composable
fun PreviewAuthMainForm() {
    MomentAppTheme {
        AuthMainForm(
            confirmBtnText = "Почати подорож",
            onConfirmBtn = {},
            onEmailTextChanged = {},
            onPasswordTextChanged = {},
        )
    }
}