package com.example.booksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.booksapp.R
import com.example.booksapp.databinding.FragmentHomeBinding
import com.example.booksapp.databinding.FragmentLoginBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
//        binding.listener = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        return binding.root

    }

}