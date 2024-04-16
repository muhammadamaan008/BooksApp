package com.example.booksapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
        binding.listener = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

//        val token = SharedPreferencesManager.getToken("TOKEN",null)
//        println("fragmen me token $token")
//        if(!token.isNullOrEmpty()){
//            lifecycleScope.launch(Dispatchers.IO){
//                val tokenVerified = viewModel.verifyToken(UserModel(token = token))
//                println("Verified: $tokenVerified")
//                if (tokenVerified) findNavController().navigate(R.id.loginFragment_to_homeFragment)
//            }
//        }

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.apply {
            btnLogin.setOnClickListener {
                loginUser()
            }

            btnDontHaveAccount.setOnClickListener {
                findNavController().navigate(R.id.loginFragment_to_signUpFragment)
            }
        }
    }

    private fun loginUser() {
        val model = UserModel(
            email = binding.editTextEmail.text.toString(),
            password = binding.editTextPassword.text.toString()
        )
        viewModel.loginSignUpClicked()
        viewModel.checkValidationAndLogin(model)
        setObservables()
    }

    private fun setObservables() {
        viewModel.loadingBar.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.progressBar.visibility = View.VISIBLE
                binding.btnLogin.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnLogin.visibility = View.VISIBLE
            }
        })
        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        })
        viewModel.navigationListener.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { navigate ->
                if (navigate) findNavController().navigate(R.id.loginFragment_to_homeFragment)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        println("in on pause")
        viewModel.loadingBar.removeObservers(viewLifecycleOwner)
        viewModel.toastMessage.removeObservers(viewLifecycleOwner)
        viewModel.navigationListener.removeObservers(viewLifecycleOwner)
    }

}
