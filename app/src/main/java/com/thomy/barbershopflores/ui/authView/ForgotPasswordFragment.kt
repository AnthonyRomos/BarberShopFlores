package com.thomy.barbershopflores.ui.authView

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.thomy.barbershopflores.R
import com.thomy.barbershopflores.core.data.model.utils.UiState
import com.thomy.barbershopflores.core.data.model.utils.hide
import com.thomy.barbershopflores.core.data.model.utils.isValidEmail
import com.thomy.barbershopflores.core.data.model.utils.show
import com.thomy.barbershopflores.core.data.model.utils.toast
import com.thomy.barbershopflores.databinding.FragmentForgotPasswordBinding
import com.thomy.barbershopflores.ui.authView.register.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    val TAG: String = "ForgotPasswordFragment"
    lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.forgotPassBtn.setOnClickListener {
            if (validation()){
                viewModel.forgotPassword(binding.emailEt.text.toString())
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observer(){
        viewModel.forgotPassword.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.forgotPassBtn.text = ""
                    binding.forgotPassProgress.show()
                }
                is UiState.Failure -> {
                    binding.forgotPassBtn.text = "Send"
                    binding.forgotPassProgress.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.forgotPassBtn.text = "Send"
                    binding.forgotPassProgress.hide()
                    toast(state.data)
                }
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true

        if (binding.emailEt.text.isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_email))
        }else{
            if (!binding.emailEt.text.toString().isValidEmail()){
                isValid = false
                toast(getString(R.string.invalid_email))
            }
        }

        return isValid
    }


}