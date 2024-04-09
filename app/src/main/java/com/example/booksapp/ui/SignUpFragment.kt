package com.example.booksapp.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.booksapp.R
import com.example.booksapp.data.local.SharedPreferencesManager
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
                viewModel.loginSignUpClicked()
                val model = UserModel(
                    email = editTextSignUpEmail.text.toString(),
                    password = editTextSignUpPassword.text.toString(),
                    pic = picPath,
                    name = editTextName.text.toString()
                )
                viewModel.checkValidationAndRegister(model)
            }
        }

        return binding.root
    }
}