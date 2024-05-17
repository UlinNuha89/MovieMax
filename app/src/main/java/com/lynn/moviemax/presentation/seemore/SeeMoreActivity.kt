package com.lynn.moviemax.presentation.seemore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.databinding.ActivitySeeMoreBinding
import com.lynn.moviemax.databinding.LayoutSheetViewBinding
import com.lynn.moviemax.presentation.seemore.adapter.SeeMoreAdapter
import com.lynn.moviemax.utils.proceedWhen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SeeMoreActivity : AppCompatActivity() {
    private val binding: ActivitySeeMoreBinding by lazy {
        ActivitySeeMoreBinding.inflate(layoutInflater)
    }
    private val viewModel: SeeMoreViewModel by viewModel {
        parametersOf(intent.extras)
    }
    private val seeMoreAdapter: SeeMoreAdapter by lazy {
        SeeMoreAdapter {
            showBottomSheetInfo(it)
        }
    }

    companion object {
        const val EXTRAS_ITEM = "EXTRAS_ITEM"

        fun startActivity(
            context: Context,
            category: String,
        ) {
            val intent = Intent(context, SeeMoreActivity::class.java)
            intent.putExtra(EXTRAS_ITEM, category)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClick()
        setUpAdapter()
        observeData()
    }

    private fun setOnClick() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun observeData() {
        viewModel.header.let {
            binding.tvHeader.text = it
            when (it) {
                "Top Rated" -> setTopRated()
                "Popular" -> setPopular()
                "Up Coming" -> setUpComing()
                "Now Playing" -> setNowPlaying()
            }
        }
    }

    private fun setUpAdapter() {
        binding.rvItem.apply {
            adapter = seeMoreAdapter
        }
    }

    private fun setNowPlaying() {
        lifecycleScope.launch {
            viewModel.nowPlaying().collectLatest {
                seeMoreAdapter.submitData(it)
            }
        }
    }

    private fun setTopRated() {
        lifecycleScope.launch {
            viewModel.topRated().collectLatest {
                seeMoreAdapter.submitData(it)
            }
        }
    }

    private fun setPopular() {
        lifecycleScope.launch {
            viewModel.popular().collectLatest {
                seeMoreAdapter.submitData(it)
            }
        }
    }

    private fun setUpComing() {
        lifecycleScope.launch {
            viewModel.upComing().collectLatest {
                seeMoreAdapter.submitData(it)
            }
        }
    }

    private fun showBottomSheetInfo(movie: Movie) {
        val bottomSheetDialog = BottomSheetDialog(this)
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
                putExtra(Intent.EXTRA_TEXT, "Check out this movie: ${movie.posterPath}")
                type = "text/plain"
            }

        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }

    private fun addToMyList(
        movie: Movie,
        binding: LayoutSheetViewBinding,
    ) {
        viewModel.addToMyList(movie).observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.btnAddMyList.isVisible = false
                    binding.btnRemoveMyList.isVisible = true
                    binding.pbLoadingList.isVisible = false
                    binding.btnRemoveMyList.setOnClickListener {
                        viewModel.removeFromMyList(movie)
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
        viewModel.checkMovieById(movie).observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.btnAddMyList.isVisible = false
                    binding.btnRemoveMyList.isVisible = true
                    binding.pbLoadingList.isVisible = false
                    binding.btnRemoveMyList.setOnClickListener {
                        removeFromMyList(movie, binding)
                    }
                },
                doOnEmpty = {
                    binding.btnAddMyList.isVisible = true
                    binding.btnRemoveMyList.isVisible = false
                    binding.pbLoadingList.isVisible = false
                    binding.btnAddMyList.setOnClickListener {
                        addToMyList(movie, binding)
                    }
                },
            )
        }
    }

    private fun removeFromMyList(
        movie: Movie,
        binding: LayoutSheetViewBinding,
    ) {
        viewModel.removeFromMyList(movie).observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.btnAddMyList.isVisible = true
                    binding.btnRemoveMyList.isVisible = false
                    binding.pbLoadingList.isVisible = false
                    binding.btnRemoveMyList.setOnClickListener {
                        viewModel.addToMyList(movie)
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
}
