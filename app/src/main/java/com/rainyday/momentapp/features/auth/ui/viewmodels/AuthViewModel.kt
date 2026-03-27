package com.rainyday.momentapp.features.auth.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.rainyday.momentapp.core.data.models.Result
import com.rainyday.momentapp.features.auth.domain.models.RegisterReqData
import com.rainyday.momentapp.features.auth.domain.models.SignInReqData
import com.rainyday.momentapp.features.auth.domain.usecases.RegisterUserUseCase
import com.rainyday.momentapp.features.auth.domain.usecases.SignInUserUseCase
import com.rainyday.momentapp.features.auth.ui.states.AuthUiState
import com.rainyday.momentapp.features.auth.ui.states.AuthUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@JvmInline
value class UserEmail(val data: String)

@JvmInline
value class UserPassword(val data: String)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUserUseCase: SignInUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val firebaseAuth: FirebaseAuth,
): ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Loading)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _authUserState = MutableStateFlow(AuthUserState())
    val authUserState: StateFlow<AuthUserState> = _authUserState.asStateFlow()

    fun registerUser(userEmail: UserEmail, userPassword: UserPassword) {
        _uiState.value = AuthUiState.Loading

        viewModelScope.launch {
            try {
                val data = RegisterReqData(
                    email = userEmail.data,
                    password = userPassword.data,
                )

                when (val authUser = registerUserUseCase(data)) {
                    is Result.Success -> {
                        _uiState.value = AuthUiState.Success(authUser.data)
                        _authUserState.update { it.copy(isAuthorized = true) }
                    }
                    is Result.Error -> {
                        _uiState.value = AuthUiState.Error(authUser.message)
                    }
                    else -> {
                        _uiState.value = AuthUiState.Error("Unknown error")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message.toString())
            }
        }
    }

    fun signInUser(userEmail: UserEmail, userPassword: UserPassword) {
        _uiState.value = AuthUiState.Loading

        viewModelScope.launch {
            try {
                val data = SignInReqData(
                    email = userEmail.data,
                    password = userPassword.data,
                )

                when (val authUser = signInUserUseCase(data)) {
                    is Result.Success -> {
                        _uiState.value = AuthUiState.Success(authUser.data)
                        _authUserState.update { it.copy(isAuthorized = true) }
                    }
                    is Result.Error -> {
                        _uiState.value = AuthUiState.Error(authUser.message)
                    }
                    else -> {
                        _uiState.value = AuthUiState.Error("Unknown error")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message.toString())
            }
        }
    }

    fun setOrUpdateEmail(email: String) {
        _authUserState.update { it.copy(email = email) }
    }

    fun setOrUpdatePassword(password: String) {
        _authUserState.update { it.copy(password = password) }
    }

    fun isUserAuthorized(): Boolean {
        val isUser = firebaseAuth.currentUser != null
        _authUserState.update { it.copy(isAuthorized = isUser) }
        return isUser
    }
}