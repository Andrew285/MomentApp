package com.rainyday.momentapp.features.auth.domain.repository

import com.rainyday.momentapp.core.data.models.Result
import com.rainyday.momentapp.features.auth.domain.models.AuthUser
import com.rainyday.momentapp.features.auth.domain.models.RegisterReqData
import com.rainyday.momentapp.features.auth.domain.models.SignInReqData

interface IAuthRepository {
    suspend fun register(data: RegisterReqData): Result<AuthUser>
    suspend fun signIn(data: SignInReqData): Result<AuthUser>
}