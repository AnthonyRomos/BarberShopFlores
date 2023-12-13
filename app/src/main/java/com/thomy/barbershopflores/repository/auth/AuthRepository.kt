package com.thomy.barbershopflores.repository.auth

import com.thomy.barbershopflores.core.data.model.model.UserLogin
import com.thomy.barbershopflores.core.data.model.model.UserRegister
import com.thomy.barbershopflores.core.data.model.utils.UiState

interface AuthRepository {
    fun registerUser(email: String, password: String, user: UserRegister, result: (UiState<String>) -> Unit)
    fun updateUserInfo(user: UserRegister, result: (UiState<String>) -> Unit)
    fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit)
    suspend fun signInWithGoogle(idToken: String, result: (UiState<String>) -> Unit)
    fun forgotPassword(email: String, result: (UiState<String>) -> Unit)
    fun logout(result: () -> Unit)
    fun storeSession(id: String, result: (UserLogin?) -> Unit)
    fun getSession(result: (UserLogin?) -> Unit)

}
