package com.rainyday.momentapp.features.auth.domain.usecases

import com.rainyday.momentapp.core.data.models.Result
import com.rainyday.momentapp.core.notifications.domain.usecases.SaveFCMTokenUseCase
import com.rainyday.momentapp.features.auth.domain.models.AuthUser
import com.rainyday.momentapp.features.auth.domain.models.RegisterReqData
import com.rainyday.momentapp.features.auth.domain.repository.IAuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: IAuthRepository,
    private val saveFCMTokenUseCase: SaveFCMTokenUseCase,
) {
    suspend operator fun invoke(data: RegisterReqData): Result<AuthUser> {
        val result = repository.register(data)
        saveFCMTokenUseCase()
        return result
    }
}