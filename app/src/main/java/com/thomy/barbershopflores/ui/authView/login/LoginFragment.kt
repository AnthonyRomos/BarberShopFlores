package com.thomy.barbershopflores.ui.authView.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.thomy.barbershopflores.R
import com.thomy.barbershopflores.core.data.model.utils.UiState
import com.thomy.barbershopflores.core.data.model.utils.dismissKeyboard
import com.thomy.barbershopflores.core.data.model.utils.hide
import com.thomy.barbershopflores.core.data.model.utils.isValidEmail
import com.thomy.barbershopflores.core.data.model.utils.loseFocusAfterAction
import com.thomy.barbershopflores.core.data.model.utils.onTextChanged
import com.thomy.barbershopflores.core.data.model.utils.show
import com.thomy.barbershopflores.core.data.model.utils.toast
import com.thomy.barbershopflores.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    val TAG: String = "LoginFragment"
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        observerLoginUser()

        binding.etEmail.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)
        binding.etEmail.onTextChanged { onFieldChanged() }

        binding.etPassword.loseFocusAfterAction(EditorInfo.IME_ACTION_DONE)
        binding.etPassword.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
        binding.etPassword.onTextChanged { onFieldChanged() }

        binding.btnLogin.setOnClickListener {
            it.dismissKeyboard()
            if (validation()) {
                loginViewModel.login(
                    email = binding.etEmail.text.toString(),
                    password = binding.etPassword.text.toString()
                )
            }
        }
        binding.forgotPassLabel.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observerLoginUser() {
        loginViewModel.login.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.btnLogin.text = ""
                    binding.progressBar.show()
                }

                is UiState.Failure -> {
                    binding.btnLogin.text = "Login"
                    binding.progressBar.hide()
                    toast(state.error)
                }

                is UiState.Success -> {
                    binding.btnLogin.text = "Login"
                    binding.progressBar.hide()
                    toast(state.data)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }

                else -> {}
            }
        }
    }

    private fun onFieldChanged(hasFocus: Boolean = false) {
        validation()
        if (!hasFocus) {
            loginViewModel.onFieldsChanged(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        }
    }

    private fun validation(): Boolean {
        var isValid = true
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isEmpty() || !email.isValidEmail()) {
            isValid = false
            binding.tilEmail.error = getString(R.string.login_error_mail)
        } else {
            binding.tilEmail.error = null
        }
        if (password.isEmpty() || password.length < 8) {
            isValid = false
            binding.tilPassword.error = getString(R.string.login_error_password)
        } else {
            binding.tilPassword.error = null
        }
        return isValid
    }

    override fun onStart() {
        super.onStart()
        loginViewModel.getSession { user ->
            if (user != null) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

