package com.lynn.moviemax.presentation.mylist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lynn.moviemax.R
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.databinding.FragmentMylistBinding
import com.lynn.moviemax.databinding.LayoutSheetViewBinding
import com.lynn.moviemax.presentation.mylist.adapter.MyListAdapter
import com.lynn.moviemax.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyListFragment : Fragment() {
    private lateinit var binding: FragmentMylistBinding
    private val viewModel: MyListViewModel by viewModel()
    private val myListAdapter: MyListAdapter by lazy {
        MyListAdapter {
            showBottomSheetInfo(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMylistBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observerMovieList()
    }

    private fun setupList() {
        binding.rvMovieList.apply {
            adapter = this@MyListFragment.myListAdapter
        }
    }

    private fun observerMovieList() {
        viewModel.getMovieList().observe(viewLifecycleOwner) { result ->
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
                },
            )
        }
    }

    private fun showBottomSheetInfo(movie: Movie) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = LayoutSheetViewBinding.inflate(layoutInflater)
        bottomSheetBinding.apply {
            ivBannerFilm.load(movie.backdropPath)
            ivPoster.load(movie.posterPath)
            tvTitleFilm.text = movie.title
            tvDescFilm.text = movie.overview
            tvRelease.text = movie.releaseDate
            tvRating.text = movie.voteAverage.toString()
        }
        checkMovie(movie, bottomSheetBinding)
        bottomSheetBinding.btnShared.setOnClickListener {
            bottomSheetDialog.dismiss()
            showShareBottomSheet(movie)
        }
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun showShareBottomSheet(movie: Movie) {
        val intent =
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Check out this movie: ${movie.title}")
                type = "text/plain"
            }

        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }

    private fun addToMyList(
        movie: Movie,
        binding: LayoutSheetViewBinding,
    ) {
        viewModel.addToMyList(movie).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.btnAddMyList.isVisible = false
                    binding.btnRemoveMyList.isVisible = true
                    binding.pbLoadingList.isVisible = false
                    binding.btnRemoveMyList.setOnClickListener {
                        viewModel.removeMovie(movie)
                        checkMovie(movie, binding)
                    }
                },
                doOnLoading = {
                    binding.btnAddMyList.isVisible = false
                    binding.btnRemoveMyList.isVisible = false
                    binding.pbLoadingList.isVisible = true
                },
            )
        }
    }

    private fun checkMovie(
        movie: Movie,
        binding: LayoutSheetViewBinding,
    ) {
        viewModel.checkMovieById(movie).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.btnAddMyList.isVisible = false
                    binding.btnRemoveMyList.isVisible = true
                    binding.pbLoadingList.isVisible = false
                    binding.btnRemoveMyList.setOnClickListener {
                        viewModel.removeMovie(movie)
                    }
                    binding.tvMyLists.text = getString(R.string.text_mylist_remove)
                },
                doOnLoading = {
                    binding.btnAddMyList.isVisible = false
                    binding.btnRemoveMyList.isVisible = false
                    binding.pbLoadingList.isVisible = true
                },
                doOnEmpty = {
                    binding.btnAddMyList.isVisible = true
                    binding.btnRemoveMyList.isVisible = false
                    binding.pbLoadingList.isVisible = false
                    binding.btnAddMyList.setOnClickListener {
                        addToMyList(movie, binding)
                    }
                    binding.tvMyLists.text = getString(R.string.text_my_list_add)
                },
            )
        }
    }
}
