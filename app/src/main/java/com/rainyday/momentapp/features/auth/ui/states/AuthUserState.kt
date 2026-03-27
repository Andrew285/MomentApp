package com.rainyday.momentapp.features.auth.ui.states

data class AuthUserState(
    val email: String = "",
    val password: String = "",
    val isAuthorized: Boolean = false,
)