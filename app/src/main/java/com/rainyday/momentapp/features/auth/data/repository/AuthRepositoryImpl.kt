package com.rainyday.momentapp.features.auth.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.rainyday.momentapp.features.auth.domain.repository.IAuthRepository
import javax.inject.Inject
import com.rainyday.momentapp.core.data.models.Result
import com.rainyday.momentapp.features.auth.domain.models.AuthUser
import com.rainyday.momentapp.features.auth.domain.models.RegisterReqData
import com.rainyday.momentapp.features.auth.domain.models.SignInReqData
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): IAuthRepository {

    override suspend fun register(data: RegisterReqData): Result<AuthUser> {
        return try {
            val email = data.email
            val password = data.password

            val authResult = firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()

            val user = authResult.user
            if (user == null) {
                Result.Error("User is empty")
            }
            else {
                Result.Success(
                    AuthUser(
                        uid = user.uid,
                        email = user.email!!,
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to register user")
        }
    }

    override suspend fun signIn(data: SignInReqData): Result<AuthUser> {
        return try {
            val email = data.email
            val password = data.password

            val authResult = firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()

            val user = authResult.user
            if (user == null) {
                Result.Error("User is empty")
            }
            else {
                Result.Success(
                    AuthUser(
                        uid = user.uid,
                        email = user.email!!,
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to register user")
        }
    }
}