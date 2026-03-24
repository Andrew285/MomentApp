package com.rainyday.momentapp.core.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rainyday.momentapp.core.ui.theme.MomentAppTheme

@Composable
fun TextFieldComponent(
    title: String,
    textFieldValue: String = "",
    placeholder: @Composable () -> Unit,
    onValueChange: (String) -> Unit,
) {
    TitleInputComponent(
        title = title
    ) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = onValueChange,
            placeholder = placeholder,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedTextColor = MaterialTheme.colorScheme.primary,
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = RoundedCornerShape(20.dp),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
@Preview(
    showBackground = true,
    backgroundColor =  0xFF0D0A14
)
@Composable
fun PreviewTextFieldComponent() {
    MomentAppTheme {
        TextFieldComponent(
            title = "Назва події",
            textFieldValue = "",
            placeholder = {
                Text(
                    text = "Романтична вечеря",
                    color = MaterialTheme.colorScheme.primary
                )
            },
            onValueChange = { }
        )
    }
}
