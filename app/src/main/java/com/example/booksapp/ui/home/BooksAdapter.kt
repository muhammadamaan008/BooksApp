package com.example.booksapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booksapp.data.model.BooksModel
import com.example.booksapp.databinding.BooksViewBinding

class BooksAdapter : RecyclerView.Adapter<BooksAdapter.MyViewHolder>() {
    private lateinit var bookList: List<BooksModel>

    fun setBooksData(booksList: List<BooksModel>) {
        bookList = booksList
    }

    class MyViewHolder(var binding: BooksViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(booksModel: BooksModel) {
            binding.bookTitleText.text = booksModel.title
            Glide.with(binding.root).load(booksModel.pic).into(binding.bookImage)
            binding.bookCardView.setOnClickListener {
                val action = ListViewFragmentDirections.actionListViewFragmentToDetailsActivity(booksModel)
                it.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = BooksViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = bookList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(bookList[position])
    }
}