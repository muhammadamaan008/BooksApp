package com.example.booksapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.booksapp.R
import com.example.booksapp.data.local.SharedPreferencesManager
import com.example.booksapp.data.model.UserModel
import com.example.booksapp.databinding.ActivityAuthBinding
import com.example.booksapp.ui.home.HomeActivity
import com.example.booksapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private val viewModel by viewModels<UserViewModel>()
    private lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { true }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)

        lifecycleScope.launch {
            setSupportActionBar(binding.toolbar)
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.grey)
            ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }
        }

        lifecycleScope.launch {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.navigation)

            val token = SharedPreferencesManager.getToken("TOKEN", null)
            if (!token.isNullOrEmpty()) {
                withContext(Dispatchers.IO) {
                    viewModel.verifyToken(UserModel(token = token))
                }
            } else {
                setupNavigation(graph, navHostFragment)
            }
            setTokenObservable(graph, navHostFragment)
        }
    }

    private fun setTokenObservable(graph: NavGraph, navHostFragment: NavHostFragment) {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.tokenVerified.observe(this@AuthActivity) { isTokenValid ->
                if (isTokenValid) {
                    navigateToHomeActivity()
                } else {
                    setupNavigation(graph, navHostFragment)
                }
            }
        }
    }

    private fun setupNavigation(graph: NavGraph, navHostFragment: NavHostFragment) {
        lifecycleScope.launch(Dispatchers.Main) {
            splashScreen.setKeepOnScreenCondition { false }
            graph.setStartDestination(R.id.loginFragment)
            navHostFragment.navController.setGraph(graph, intent.extras)
            setAppBar(navHostFragment.navController)
        }
    }

    private fun navigateToHomeActivity() {
        lifecycleScope.launch(Dispatchers.Default) {
            splashScreen.setKeepOnScreenCondition { false }
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finish()
        }
    }

    private fun setAppBar(navController: NavController) {
        lifecycleScope.launch {
            setupActionBarWithNavController(navController)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(binding.fragmentContainerView.id).navigateUp() || super.onSupportNavigateUp()
    }
}
