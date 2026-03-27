package com.rainyday.momentapp.core.notifications.domain.usecases

import com.google.firebase.messaging.FirebaseMessaging
import com.rainyday.momentapp.core.data.models.Result
import com.rainyday.momentapp.core.notifications.domain.repository.IFCMRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SaveFCMTokenUseCase @Inject constructor(
    private val repository: IFCMRepository,
    private val firebaseMessaging: FirebaseMessaging,
) {
    suspend operator fun invoke(token: String? = null): Result<Unit> {
        return try {
            val resultToken = token ?: firebaseMessaging.token.await()
            repository.saveToken(resultToken)
        } catch (e: Exception) {
            Result.Error("Token is not retrieved")
        }
    }
}