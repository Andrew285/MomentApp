package com.rainyday.momentapp.core.notifications.domain.repository

import com.rainyday.momentapp.core.data.models.Result

interface IFCMRepository {
    suspend fun getToken(): Result<String>
    suspend fun saveToken(token: String): Result<Unit>
}