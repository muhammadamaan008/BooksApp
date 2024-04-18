package com.example.booksapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.booksapp.R
import com.example.booksapp.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)
        lifecycleScope.launch {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homeActivityMain)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.homeActivityFragmentContainerView) as NavHostFragment

        val bottomNavBar = findViewById<BottomNavigationView>(R.id.homeActivityNavigationView)
        val navigationController = navHostFragment.navController
//        val appBarConfig = AppBarConfiguration(setOf(R.id.firstFragment,R.id.secondFragment,R.id.thirdFragment))
//        setupActionBarWithNavController(navigationController,appBarConfig)
        bottomNavBar.setupWithNavController(navigationController)
    }
}