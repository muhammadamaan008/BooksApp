package com.example.booksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booksapp.databinding.FragmentListViewBinding
import com.example.booksapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListViewFragment : Fragment() {
    private lateinit var binding: FragmentListViewBinding
    private val viewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentListViewBinding.inflate(inflater, container, false)
//        binding.listener = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        lifecycleScope.launch {
            viewModel.getAllAuthors()
        }
        lifecycleScope.launch {
            viewModel.getAllBooks()
        }

//        binding.getAuthorTokenBtn.setOnClickListener {
//            lifecycleScope.launch(Dispatchers.IO) {
//                viewModel.getAllAuthors()
//                viewModel.getAllBooks()
//            }
//        }

//        val  recyclerView = binding.authorsRecyclerView.id
        binding.authorsRecyclerView.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        binding.authorsRecyclerView.setHasFixedSize(true)
        viewModel.authorDataset.observe(viewLifecycleOwner,Observer{
            val adapter = AuthorsAdapter()
            adapter.setAuthorsData(it)
            binding.authorsRecyclerView.adapter = adapter
        })


        binding.booksRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.booksRecyclerView.setHasFixedSize(true)
        viewModel.booksDataset.observe(viewLifecycleOwner,Observer{
            val adapter = BooksAdapter()
            adapter.setBooksData(it)
            binding.booksRecyclerView.adapter = adapter
        })

        viewModel.loadingBar.observe(viewLifecycleOwner,Observer{
            if (it == true){
                binding.authorsRecyclerView.visibility = View.INVISIBLE
                binding.loadingBar.visibility = View.VISIBLE
            }
            else{
                binding.authorsRecyclerView.visibility = View.VISIBLE
                binding.loadingBar.visibility = View.GONE
            }
        })

        return binding.root
    }
}