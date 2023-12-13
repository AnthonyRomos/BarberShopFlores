package com.thomy.barbershopflores.ui.authView.register

data class RegisterViewState(

val isLoading: Boolean = false,
val isValidEmail: Boolean = true,
val isValidPassword: Boolean = true,


){
    fun userValidated() = isValidEmail && isValidPassword
}

