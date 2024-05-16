package com.lynn.moviemax.presentation.seemore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.databinding.ActivitySeeMoreBinding
import com.lynn.moviemax.databinding.LayoutSheetViewBinding
import com.lynn.moviemax.presentation.seemore.adapter.SeeMoreAdapter
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
        fun startActivity(context: Context, category: String) {
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
        bottomSheetBinding.btnShared.setOnClickListener {
            bottomSheetDialog.dismiss()
            showShareBottomSheet(movie)
        }
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun showShareBottomSheet(movie: Movie) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out this movie: ${movie.title}")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }
}