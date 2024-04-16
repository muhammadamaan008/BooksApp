package com.example.booksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.booksapp.R
import com.example.booksapp.databinding.FragmentHomeBinding
import com.example.booksapp.databinding.FragmentLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
//        binding.listener = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val appCompatActivity = requireActivity() as AppCompatActivity

        // Find the NavHostFragment
        val navHostFragment = childFragmentManager.findFragmentById(R.id.my_nav_host_fragmen) as NavHostFragment

        // Get the NavController associated with this NavHostFragment
        val navController = navHostFragment.navController

        // Find the BottomNavigationView
        val bottomNavView = activity?.findViewById<BottomNavigationView>(R.id.navigationView)



        // Setup the bottom navigation view with the NavController
//        if (navController != null) {
            bottomNavView?.setupWithNavController(navController)
//        }
        return binding.root
    }

}