package com.example.booksapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.booksapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        lifecycleScope.launch {
            setSupportActionBar(binding.toolbar)
            setAppBar()
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.grey)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }
        }
    }

    private fun setAppBar(){
       lifecycleScope.launch {
           val navHostFragment =
               supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
           val navController = navHostFragment.navController
           setupActionBarWithNavController(navController)
       }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(binding.fragmentContainerView.id).navigateUp() || super.onSupportNavigateUp()
    }
}