package com.example.booksapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.booksapp.R
import com.example.booksapp.databinding.FragmentLoginBinding
import com.example.booksapp.viewmodel.UserViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.btnDontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.loginFragment_to_signUpFragment)
        }
    }
}
