package com.example.booksapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.booksapp.R
import com.example.booksapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login, container, false
        )

        binding.apply {
            btnDontHaveAccount.setOnClickListener {
                findNavController().navigate(R.id.loginFragment_to_signUpFragment)
            }
            btnLogin.setOnClickListener {
                loginBtnClicked()
            }
        }

        setUpEmailError()
        setUpPasswordError()

        return binding.root
    }

    private fun setUpEmailError() {
        binding.editTextEmail.addTextChangedListener {
            emailChangeListener(binding.editTextEmail)
        }
    }

    private fun setUpPasswordError() {
        binding.editTextPassword.addTextChangedListener {
            passwordChangeListener(binding.editTextPassword)
        }
    }

    private fun emailChangeListener(editText: EditText) {
        if (!editText.text.matches(Regex(emailPattern))) {
            binding.layoutEmail.apply {
                error = getString(R.string.valid_email_required)
                isErrorEnabled = true
            }
        } else {
            binding.layoutEmail.apply {
                error = null
                isErrorEnabled = false
            }
        }
    }

    private fun passwordChangeListener(editText: EditText) {
        if (editText.text.length in 1..4) {
            binding.layoutPassword.apply {
                error = getString(R.string.password_length)
                isErrorEnabled = true
            }
        } else {
            binding.layoutPassword.apply {
                error = null
                isErrorEnabled = false
            }
        }
    }

    private fun loginBtnClicked() {
        if (binding.editTextEmail.text.isNullOrBlank()) {
            binding.layoutEmail.apply {
                error = getString(R.string.email_required)
                isErrorEnabled = true
            }
        } else if (binding.editTextPassword.text.isNullOrBlank()) {
            binding.layoutPassword.apply {
                error = getString(R.string.password_required)
                isErrorEnabled = true
            }
        }
    }

}