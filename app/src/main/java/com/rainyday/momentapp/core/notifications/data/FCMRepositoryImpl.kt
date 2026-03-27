package com.rainyday.momentapp.core.notifications.data

import com.google.firebase.messaging.FirebaseMessaging
import com.rainyday.momentapp.core.notifications.data.remote.IFirebaseCloudMessagingApiService
import com.rainyday.momentapp.core.notifications.data.remote.dto.SaveTokenRequest
import com.rainyday.momentapp.core.notifications.domain.repository.IFCMRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.rainyday.momentapp.core.data.models.Result
import okio.IOException

class FCMRepositoryImpl @Inject constructor(
    private val firebaseMessaging: FirebaseMessaging,
    private val apiInstance: IFirebaseCloudMessagingApiService,
): IFCMRepository {
    override suspend fun getToken(): Result<String> {
        return try {
            val token = firebaseMessaging.token.await()
            Result.Success(token)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to retrieve FCM token")
        }
    }

    override suspend fun saveToken(token: String): Result<Unit> {
        return try {
            val response = apiInstance.saveToken(
                tokenRequest = SaveTokenRequest(token = token)
            )

            if (response.isSuccessful) {
                Result.Success(Unit)
            }
            else {
                Result.Error("Failed to save token: ${response.code()}")
            }
        }
        catch (e: IOException) {
            Result.Error("No internet connection")
        }
        catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }
}