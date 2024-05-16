package com.lynn.moviemax.presentation.mylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.lynn.moviemax.databinding.FragmentMylistBinding
import com.lynn.moviemax.presentation.mylist.adapter.MyListAdapter
import com.lynn.moviemax.utils.proceed
import com.lynn.moviemax.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class MylistFragment : Fragment() {
    private lateinit var binding: FragmentMylistBinding
    private val viewModel: MyListViewModel by viewModel()
    private val myListAdapter: MyListAdapter by lazy {
        MyListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentMylistBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observerMovieList()
        setClick()
    }

    // Hapus disini
    private fun setClick() {
        binding.btnSatu.setOnClickListener {
            addToMyList()
        }
        binding.btnDua.setOnClickListener {
            removeFromMyList()
        }
    }

    private fun removeFromMyList() {
        viewModel.removeMovie(viewModel.mockMovie())
    }

    private fun addToMyList() {
        viewModel.addToMylist().observe(viewLifecycleOwner){
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        requireContext(),
                        "Sukses",
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnError = {
                    Toast.makeText(requireContext(),"gagal", Toast.LENGTH_SHORT)
                        .show()
                },
                doOnLoading = {
                    Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
                },
            )
        }
    }
//    Sampe sini

    private fun setupList() {
        binding.rvMovieList.apply {
            adapter = this@MylistFragment.myListAdapter
        }
    }

    private fun observerMovieList() {
        viewModel.getMovieList().observe(viewLifecycleOwner){result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.rvMovieList.isVisible = true
                    binding.tvError.isVisible = false
                    binding.pbLoadingList.isVisible = false
                    binding.ivEmptyState.root.isVisible = false
                    result.payload?.let {
                        myListAdapter.submitMovies(it)
                    }
                },
                doOnError = {
                    binding.rvMovieList.isVisible = false
                    binding.tvError.isVisible = true
                    binding.tvError.text = result.exception?.message.orEmpty()
                    binding.pbLoadingList.isVisible = false
                    binding.ivEmptyState.root.isVisible = false
                },
                doOnLoading = {
                    binding.rvMovieList.isVisible = false
                    binding.pbLoadingList.isVisible = true
                    binding.tvError.isVisible = false
                    binding.ivEmptyState.root.isVisible = false
                },
                doOnEmpty = {
                    binding.rvMovieList.isVisible = false
                    binding.tvError.isVisible = false
                    binding.ivEmptyState.root.isVisible = true
                    binding.pbLoadingList.isVisible = false
                }
            )
        }
    }




}