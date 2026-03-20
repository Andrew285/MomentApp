package com.rainyday.momentapp.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rainyday.momentapp.R

@Composable
fun ErrorState(
    errorMessage: String,
    onBtnRetry: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Text(
                text = errorMessage
            )

            Spacer(modifier = Modifier.height(20.dp))

            BasicButton(
                text = stringResource(R.string.repeat),
                onClick = { onBtnRetry() },
                modifier = Modifier
                    .padding(horizontal = 30.dp)
            )
        }
    }
}