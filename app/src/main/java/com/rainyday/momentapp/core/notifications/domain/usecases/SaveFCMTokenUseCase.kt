package com.rainyday.momentapp.core.notifications.domain.usecases

import com.rainyday.momentapp.core.notifications.domain.repository.IFCMRepository
import javax.inject.Inject

class SaveFCMTokenUseCase @Inject constructor(
    private val repository: IFCMRepository
) {
    suspend operator fun invoke(token: String) {
        repository.saveToken(token)
    }
}