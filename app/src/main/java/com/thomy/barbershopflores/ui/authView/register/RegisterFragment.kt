package com.thomy.barbershopflores.ui.authView.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.thomy.barbershopflores.R
import com.thomy.barbershopflores.core.data.model.model.UserRegister
import com.thomy.barbershopflores.core.data.model.utils.UiState
import com.thomy.barbershopflores.core.data.model.utils.dismissKeyboard
import com.thomy.barbershopflores.core.data.model.utils.hide
import com.thomy.barbershopflores.core.data.model.utils.isValidEmail
import com.thomy.barbershopflores.core.data.model.utils.loseFocusAfterAction
import com.thomy.barbershopflores.core.data.model.utils.onTextChanged
import com.thomy.barbershopflores.core.data.model.utils.show
import com.thomy.barbershopflores.core.data.model.utils.toast
import com.thomy.barbershopflores.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    val TAG: String = "RegisterFragment"
    lateinit var binding: FragmentRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()

        binding.etUserName.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)
        binding.etUserName.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
        binding.etUserName.onTextChanged { onFieldChanged() }

        binding.etRegisterEmail.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)
        binding.etRegisterEmail.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
        binding.etRegisterEmail.onTextChanged { onFieldChanged() }

        binding.etRegisterPassword.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)
        binding.etRegisterPassword.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
        binding.etRegisterPassword.onTextChanged { onFieldChanged() }

        binding.etRepeatPassword.loseFocusAfterAction(EditorInfo.IME_ACTION_DONE)
        binding.etRepeatPassword.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
        binding.etRepeatPassword.onTextChanged { onFieldChanged() }

        binding.btnCreateUser.setOnClickListener {
            it.dismissKeyboard()
            if (validation()) {
                registerViewModel.register(
                    email = binding.etRegisterEmail.text.toString(),
                    password = binding.etRegisterPassword.text.toString(),
                    userRegister = getUserObj()
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observer() {
        registerViewModel.register.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.btnCreateUser.text = ""
                    binding.progressBar.show()
                }

                is UiState.Failure -> {
                    binding.btnCreateUser.text = "Register"
                    binding.progressBar.hide()
                    toast(state.error)
                }

                is UiState.Success -> {
                    binding.btnCreateUser.text = "Register"
                    binding.progressBar.hide()
                    toast(state.data)
                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                }

                else -> {}
            }
        }
    }

    private fun getUserObj(): UserRegister {
        return UserRegister(
            id = "",
            userName = binding.etUserName.text.toString(),
            email = binding.etRegisterEmail.text.toString(),
            password = binding.etRegisterPassword.text.toString(),
            passwordConfirmation = binding.etRepeatPassword.text.toString()
        )
    }

    private fun validation(): Boolean {
        var isValid = true

        val email = binding.etRegisterEmail.text.toString()
        val password = binding.etRegisterPassword.text.toString()
        val repeatPassword = binding.etRepeatPassword.text.toString()

        if (binding.etUserName.text.isNullOrEmpty() || binding.etUserName.length() < 6) {
            isValid = false
            binding.tilRegisterUser.error = getString(R.string.enter_name_user)
        } else {
            binding.tilRegisterUser.error = null
        }

        if (email.isEmpty() || !email.isValidEmail()) {
            isValid = false
            binding.tilRegisterEmail.error = getString(R.string.login_error_mail)
        } else {
            binding.tilRegisterEmail.error = null
        }

        if (password.isEmpty() || password.length < 8) {
            isValid = false
            binding.tilRegisterPassword.error = getString(R.string.login_error_password)
        } else {
            binding.tilRegisterPassword.error = null
        }
        if (repeatPassword.isEmpty() || password.length < 8 || password != repeatPassword) {
            isValid = false
            binding.tilRepeatPassword.error = getString(R.string.passwords_not_match)
        } else {
            binding.tilRepeatPassword.error = null
        }
        return isValid
    }

    private fun onFieldChanged(hasFocus: Boolean = false) {
        validation()
        if (!hasFocus) {
            registerViewModel.onFieldsChanged(
                UserRegister(
                    userName = binding.etUserName.text.toString(),
                    email = binding.etRegisterEmail.text.toString(),
                    password = binding.etRegisterPassword.text.toString(),
                    passwordConfirmation = binding.etRepeatPassword.text.toString()
                )
            )
        }
    }
}