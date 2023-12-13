package com.thomy.barbershopflores.ui.barbersView.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.thomy.barbershopflores.R
import com.thomy.barbershopflores.databinding.FragmentInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val infoViewModel: InfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerNavigate()
    }

    private fun observerNavigate() {
        binding.btnCloseSession.setOnClickListener {
            infoViewModel.logout {
                findNavController().navigate(R.id.action_infoFragment_to_optionsLoginFragment)
            }
        }
    }
}