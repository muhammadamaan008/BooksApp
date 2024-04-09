package com.example.booksapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.booksapp.R
import com.example.booksapp.data.model.UserModel
import com.example.booksapp.databinding.FragmentLoginBinding
import com.example.booksapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
//        binding.listener = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupListeners()


        return binding.root
    }

    private fun setupListeners() {
        binding.apply {
            btnDontHaveAccount.setOnClickListener {
                viewModel.loginSignUpClicked()
                val model = UserModel(
                    email = editTextEmail.text.toString(),
                    password = editTextPassword.text.toString()
                )
                viewModel.setUserData(model)
                viewModel.userLogin()

                findNavController().navigate(R.id.loginFragment_to_signUpFragment)
            }
        }
    }
}
