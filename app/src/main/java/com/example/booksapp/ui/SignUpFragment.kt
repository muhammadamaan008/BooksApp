package com.example.booksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.booksapp.R
import com.example.booksapp.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_up, container, false
        )
        setUpEmailError()
        setUpPasswordError()
        setUpConfirmPasswordError()

        return binding.root
    }

    private fun setUpEmailError() {
        binding.editTextSignUpEmail.addTextChangedListener {
            emailChangeListener(binding.editTextSignUpEmail)
        }
    }

    private fun setUpPasswordError() {
        binding.editTextSignUpPassword.addTextChangedListener {
            passwordChangeListener(binding.editTextSignUpPassword)
        }
    }

    private fun setUpConfirmPasswordError() {
        binding.editTextConfirmPassword.addTextChangedListener {
            confirmPasswordChangeListener(binding.editTextConfirmPassword)
        }
    }

    private fun emailChangeListener(editText: EditText) {
        if (!editText.text.matches(Regex(emailPattern))) {
            binding.layoutSignUpEmail.apply {
                error = getString(R.string.valid_email_required)
                isErrorEnabled = true
            }
        } else {
            binding.layoutSignUpEmail.apply {
                error = null
                isErrorEnabled = false
            }
        }
    }

    private fun passwordChangeListener(editText: EditText) {
        if (editText.text.length in 1..4) {
            binding.layoutSignUpPassword.apply {
                error = getString(R.string.password_length)
                isErrorEnabled = true
            }
        } else {
            binding.layoutSignUpPassword.apply {
                error = null
                isErrorEnabled = false
            }
        }
    }

    private fun confirmPasswordChangeListener(editText: EditText) {
        val password = binding.editTextSignUpPassword.text.toString()

        if (password.isBlank()) {
            binding.layoutSignUpPassword.apply {
                error = getString(R.string.password_required)
                isErrorEnabled = true
            }
        } else if (editText.text.toString() != password) {
            binding.layoutConfirmPassword.apply {
                error = getString(R.string.password_same)
                isErrorEnabled = true
            }
        } else {
            binding.layoutConfirmPassword.apply {
                error = null
                isErrorEnabled = false
            }
        }
    }


}