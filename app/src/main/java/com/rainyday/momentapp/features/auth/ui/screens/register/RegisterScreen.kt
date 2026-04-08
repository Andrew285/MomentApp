package com.rainyday.momentapp.features.auth.ui.screens.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rainyday.momentapp.R
import com.rainyday.momentapp.core.ui.components.LoadingContentState
import com.rainyday.momentapp.features.auth.ui.components.AuthMainForm
import com.rainyday.momentapp.features.auth.ui.states.AuthUiState
import com.rainyday.momentapp.features.auth.ui.states.AuthUserState
import com.rainyday.momentapp.features.auth.ui.viewmodels.AuthViewModel
import com.rainyday.momentapp.features.auth.ui.viewmodels.UserEmail
import com.rainyday.momentapp.features.auth.ui.viewmodels.UserPassword

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    showSnackBar: (String) -> Unit,
    onSuccess: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val userFormState by viewModel.authUserState.collectAsState()

    when (uiState) {
        is AuthUiState.Loading -> Unit
        is AuthUiState.Error -> showSnackBar((uiState as AuthUiState.Error).message)
        is AuthUiState.Success -> {
            showSnackBar(stringResource(R.string.register_successfully))
            onSuccess()
        }
    }

    AuthRegisterContent(
        userFormState = userFormState,
        onRegisterBtnClick = { email, password ->
            viewModel.registerUser(email, password)
        },
        onEmailTextChanged = { newEmail ->
            viewModel.setOrUpdateEmail(newEmail)
        },
        onPasswordTextChanged = { newPassword ->
            viewModel.setOrUpdatePassword(newPassword)
        }
    )
}

@Composable
fun AuthRegisterContent(
    userFormState: AuthUserState,
    onEmailTextChanged: (String) -> Unit,
    onPasswordTextChanged: (String) -> Unit,
    onRegisterBtnClick: (UserEmail, UserPassword) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        AuthMainForm(
            email = UserEmail(userFormState.email),
            password = UserPassword(userFormState.password),
            confirmBtnText = stringResource(R.string.register_title),
            onConfirmBtn = {
                onRegisterBtnClick(
                    UserEmail(userFormState.email),
                    UserPassword(userFormState.password)
                )
            },
            onEmailTextChanged = onEmailTextChanged,
            onPasswordTextChanged = onPasswordTextChanged
        )
    }
}

@Preview
@Composable
fun PreviewRegisterScreen() {
    AuthRegisterContent(
        userFormState = AuthUserState(),
        onEmailTextChanged = {},
        onPasswordTextChanged = {},
        onRegisterBtnClick = { _, _ -> }
    )
}