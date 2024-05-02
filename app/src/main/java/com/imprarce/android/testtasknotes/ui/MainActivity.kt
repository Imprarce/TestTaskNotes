package com.imprarce.android.testtasknotes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.imprarce.android.testtasknotes.R
import com.imprarce.android.testtasknotes.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = (supportFragmentManager.findFragmentById(R.id.containerView) as NavHostFragment).navController
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == com.imprarce.android.feature_notes.R.id.notesFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}