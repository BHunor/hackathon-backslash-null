package edu.hackathon.moviematch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController


import edu.hackathon.moviematch.R
import edu.hackathon.moviematch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    val bottomNavView get() = binding.bottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        binding.bottomNavigationView.visibility = View.GONE

        val navController = (binding.navHostFragmentContainer.getFragment() as NavHostFragment).navController
        binding.bottomNavigationView.setupWithNavController(navController)

        setContentView(binding.root)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}