package com.rainyday.momentapp.core.data.models

data class ApiResponse<T>(
    val data: T? = null,
    val success: Boolean,
    val message: String? = null,
    val error: String? = null,
)