package com.example.booksapp.ui.home.listView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booksapp.databinding.FragmentListViewBinding
import com.example.booksapp.ui.home.HomeActivity
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
        binding.lifecycleOwner = viewLifecycleOwner
        (requireActivity() as HomeActivity).supportActionBar?.hide()

        (activity as AppCompatActivity?)?.setSupportActionBar(binding.listViewToolbar)
        val toolbar = (activity as AppCompatActivity?)?.supportActionBar
        lifecycleScope.launch (Dispatchers.IO){
            viewModel.getUserDataFromDb()
        }

//        viewModel.userDataFromDb.observe(viewLifecycleOwner, Observer {
//            toolbar?.title = it.toString()
//        })

//        toolbar?.title = "homeee"


        lifecycleScope.launch {
            viewModel.getAllAuthors()
        }
        lifecycleScope.launch {
            viewModel.getAllBooks()
        }

        setLoadingObservable()
        setAuthorsData()
        setBooksData()

        return binding.root
    }

    private fun setLoadingObservable() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.loadingBar.observe(viewLifecycleOwner) {
                if (it == true) {
                    binding.authorsRecyclerView.visibility = View.GONE
                    binding.booksRecyclerView.visibility = View.GONE
                    binding.authorsText.visibility = View.GONE
                    binding.booksText.visibility = View.GONE
                    binding.loadingBar.visibility = View.VISIBLE
                } else {
                    binding.authorsRecyclerView.visibility = View.VISIBLE
                    binding.booksRecyclerView.visibility = View.VISIBLE
                    binding.authorsText.visibility = View.VISIBLE
                    binding.booksText.visibility = View.VISIBLE
                    binding.loadingBar.visibility = View.GONE
                }
            }
        }
    }

    private fun setBooksData() {
        binding.booksRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.booksRecyclerView.setHasFixedSize(true)
        viewModel.booksDataset.observe(viewLifecycleOwner) {
            val adapter = BooksAdapter()
            adapter.setBooksData(it)
            binding.booksRecyclerView.adapter = adapter
        }

//        val spacingInPixels = resources.getDimensionPixelSize(1)
//        binding.booksRecyclerView.addItemDecoration(GridSpacingItemDecoration(2, spacingInPixels, true))

//        binding.booksRecyclerView.addItemDecoration()

    }

    private fun setAuthorsData() {
        binding.authorsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.authorsRecyclerView.setHasFixedSize(true)
        viewModel.authorDataset.observe(viewLifecycleOwner) {
            val adapter = AuthorsAdapter()
            adapter.setAuthorsData(it)
            binding.authorsRecyclerView.adapter = adapter
        }
    }
}