package com.example.booksapp.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.example.booksapp.R
import com.example.booksapp.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private val args: DetailsActivityArgs by navArgs()
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.titleText.text = args.bookDetails.title
        binding.authorText.text = args.bookDetails.author
        binding.aboutText.text = args.bookDetails.about
        Glide.with(this).load(args.bookDetails.pic).into(binding.bookImg)

        println(args.bookDetails.toString())
    }
}