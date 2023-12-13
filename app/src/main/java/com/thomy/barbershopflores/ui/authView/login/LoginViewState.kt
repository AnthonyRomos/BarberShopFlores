package com.thomy.barbershopflores.ui.authView.login

data class LoginViewState(
    val isLoading: Boolean = false,
    val isValidEmail: Boolean = true,
    val isValidPassword: Boolean = true

)