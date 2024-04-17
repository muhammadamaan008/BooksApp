package com.example.booksapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booksapp.data.model.AuthorModel
import com.example.booksapp.databinding.AuthorsViewBinding

class AuthorsAdapter : RecyclerView.Adapter<AuthorsAdapter.MyViewHolder>() {
    private lateinit var authorsData: List<AuthorModel>

    fun setAuthorsData(authorData: List<AuthorModel>) {
        authorsData = authorData
    }

    class MyViewHolder(val binding: AuthorsViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setViewData(authorModel: AuthorModel) {
            binding.authorsName.text = authorModel.name
            Glide.with(binding.root)
                .load(authorModel.pic)
                .into(binding.authorsImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AuthorsViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = authorsData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setViewData(authorsData[position])
    }
}