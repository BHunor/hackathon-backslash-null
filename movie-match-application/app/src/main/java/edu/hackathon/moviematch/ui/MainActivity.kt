package edu.hackathon.moviematch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.findNavController


import edu.hackathon.moviematch.R
import edu.hackathon.moviematch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.fragment_welcome_search)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //navController = binding.navHostFragmentContainer.findNavController()
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.fragment_feed -> {
                    navController.navigateUp()
                    navController.navigate(R.id.fragment_feed)
                }
                R.id.fragment_mood -> {
                    navController.navigateUp()
                    navController.navigate(R.id.fragment_mood)
                }
//                R.id.thirdBotNav -> {
//                    navController.navigateUp()
//                    navController.navigate(R.id.profileDetailFragment)
//                }
            }
            true
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}