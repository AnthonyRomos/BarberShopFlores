package com.thomy.barbershopflores.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.thomy.barbershopflores.R
import com.thomy.barbershopflores.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        screenSplash.setKeepOnScreenCondition { false }
    }

    private fun initUI() {
        initNavigation()
    }

    private fun initNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.citasFragment, R.id.infoFragment -> {
                    binding.bottomNavView.visibility = View.VISIBLE
                }
                else -> {
                    binding.bottomNavView.visibility = View.GONE
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val currentDestination = navController.currentDestination
        when (currentDestination?.id) {
            R.id.forgotPasswordFragment -> {
                navController.navigate(R.id.loginFragment)
            }
            R.id.registerFragment -> {
                navController.navigate(R.id.optionsLoginFragment)
            }
            R.id.loginFragment -> {
                navController.navigate(R.id.optionsLoginFragment)
            }
            else -> {
                finish()
            }
        }
    }
}