package com.yonjar.pokemonrevamp.ui.mainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yonjar.pokemonrevamp.R
import com.yonjar.pokemonrevamp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashScreen.setKeepOnScreenCondition { false }

        val navMenu = binding.navMenu

        val hostFragment =
            supportFragmentManager.findFragmentById(R.id.navContainer) as NavHostFragment

        val navController = hostFragment.navController

        navMenu.setupWithNavController(navController)

        binding.ivBack.setOnClickListener {

        }
    }
}