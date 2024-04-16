package com.example.booksapp.ui.auth

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.booksapp.R
import com.example.booksapp.data.model.UserModel
import com.example.booksapp.databinding.FragmentSignUpBinding
import com.example.booksapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: UserViewModel
    private var picPath: String? = null
    private val pickMedia = registerForActivityResult(
        PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            picPath = uri.path.toString()
            binding.imageUser.setImageURI(uri)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_up, container, false
        )
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        binding.listener = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.apply {
            imageUser.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
            }

            btnAlreadyHaveAccount.setOnClickListener {
                findNavController().navigate(R.id.signUpFragment_to_loginFragment)
            }

            btnCreateAccount.setOnClickListener {
                registerUser()
            }
        }


        return binding.root
    }

    private fun registerUser() {
        viewModel.loginSignUpClicked()
        setObservables()
        val model = UserModel(
            email = binding.editTextSignUpEmail.text.toString(),
            password = binding.editTextSignUpPassword.text.toString(),
            pic = picPath,
            name = binding.editTextName.text.toString()
        )
        viewModel.checkValidationAndRegister(model)
    }

    private fun setObservables() {
        viewModel.loadingBar.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.progressBarSignup.visibility = View.VISIBLE
                binding.btnCreateAccount.visibility = View.GONE
            } else {
                binding.progressBarSignup.visibility = View.GONE
                binding.btnCreateAccount.visibility = View.VISIBLE
            }
        })

        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.navigationListener.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { navigate ->
                if (navigate) findNavController().navigate(R.id.signUpFragment_to_loginFragment)
            }
        })
    }
}