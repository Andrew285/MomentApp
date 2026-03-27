package com.rainyday.momentapp.features.auth.domain.usecases

import com.rainyday.momentapp.core.data.models.Result
import com.rainyday.momentapp.core.notifications.domain.usecases.SaveFCMTokenUseCase
import com.rainyday.momentapp.features.auth.domain.models.AuthUser
import com.rainyday.momentapp.features.auth.domain.models.SignInReqData
import com.rainyday.momentapp.features.auth.domain.repository.IAuthRepository
import javax.inject.Inject

class SignInUserUseCase @Inject constructor(
    private val repository: IAuthRepository,
    private val saveFCMTokenUseCase: SaveFCMTokenUseCase,
) {
    suspend operator fun invoke(data: SignInReqData): Result<AuthUser> {
        val result = repository.signIn(data)
        saveFCMTokenUseCase()
        return result
    }
}