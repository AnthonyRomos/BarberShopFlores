package com.thomy.barbershopflores.ui.barbersView.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.chrisbanes.photoview.PhotoView
import com.thomy.barbershopflores.core.data.model.model.Barbers
import com.thomy.barbershopflores.R
import com.thomy.barbershopflores.databinding.FragmentHomeBinding
import com.thomy.barbershopflores.ui.adapters.CortesAdapter
import com.thomy.barbershopflores.ui.barbersView.appointment.AgendarCitasViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var cortesAdapter: CortesAdapter
    private val listBarbers: MutableList<Barbers> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        observerAnimation()
        observerListCortes()
        getService()
        observerNavigate()
    }

   private fun observerNavigate() {
        binding.btnRegisterCitas.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_agendarCitasActivity)
        }
    }

    private fun observerListCortes() {
        val recyclerViewBarbers = binding.rcvCortes
        recyclerViewBarbers.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        cortesAdapter = context?.let {
            CortesAdapter(it, listBarbers)
        }!!
        recyclerViewBarbers.setHasFixedSize(true)
        recyclerViewBarbers.adapter = cortesAdapter
        val itemAnimator = DefaultItemAnimator()
        recyclerViewBarbers.itemAnimator = itemAnimator
    }

    private fun observerAnimation() {
        val photoView: PhotoView = findViewById(R.id.iv_poster_image)
        getService()

        photoView.setOnClickListener {
            if (photoView.scale == 1.0f) {
                photoView.setScale(3.0f, true)
            } else {
                photoView.setScale(1.0f, true)
            }
        }
    }

    private fun findViewById(ivPosterImage: Int): PhotoView {
        return PhotoView(context)
    }

    private fun getService() {
        val barber1 = Barbers(R.drawable.corte1, "cortes")
        listBarbers.add(barber1)
        val barber2 = Barbers(R.drawable.corte2, "cortes")
        listBarbers.add(barber2)
        val barber3 = Barbers(R.drawable.corte3, "cortes")
        listBarbers.add(barber3)
        val barber4 = Barbers(R.drawable.corte4, "cortes")
        listBarbers.add(barber4)
        val barber5 = Barbers(R.drawable.corte5, "cortes")
        listBarbers.add(barber5)
        val barber6 = Barbers(R.drawable.corte6, "cortes")
        listBarbers.add(barber6)
        val barber7 = Barbers(R.drawable.corte7, "cortes")
        listBarbers.add(barber7)
        val barber8 = Barbers(R.drawable.corte8, "cortes")
        listBarbers.add(barber8)
        val barber9 = Barbers(R.drawable.corte9, "cortes")
        listBarbers.add(barber9)
        val barber10 = Barbers(R.drawable.corte10, "cortes")
        listBarbers.add(barber10)
        val barber11 = Barbers(R.drawable.corte11, "cortes")
        listBarbers.add(barber11)
        val barber12 = Barbers(R.drawable.corte12, "cortes")
        listBarbers.add(barber12)
        val barber13 = Barbers(R.drawable.corte13, "cortes")
        listBarbers.add(barber13)
        val barber14 = Barbers(R.drawable.corte14, "cortes")
        listBarbers.add(barber14)
        val barber15 = Barbers(R.drawable.corte15, "cortes")
        listBarbers.add(barber15)
        val barber16 = Barbers(R.drawable.corte16, "cortes")
        listBarbers.add(barber16)
        val barber17 = Barbers(R.drawable.corte17, "cortes")
        listBarbers.add(barber17)
        val barber18 = Barbers(R.drawable.corte18, "cortes")
        listBarbers.add(barber18)
        val barber19 = Barbers(R.drawable.corte19, "cortes")
        listBarbers.add(barber19)
        val barber20 = Barbers(R.drawable.corte20, "cortes")
        listBarbers.add(barber20)
    }

    @SuppressLint("SetTextI18n")
    private fun observe() {
        viewModel.loadUserProfile()
        viewModel.userProfileLiveData.observe(viewLifecycleOwner) { userProfile ->
            if (userProfile != null) {
                binding.userTextView.text = " ${userProfile.userName}"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}