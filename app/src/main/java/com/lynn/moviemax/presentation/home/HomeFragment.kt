package com.lynn.moviemax.presentation.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lynn.moviemax.R
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.databinding.FragmentHomeBinding
import com.lynn.moviemax.databinding.LayoutSheetViewBinding
import com.lynn.moviemax.presentation.home.adapter.MovieAdapter
import com.lynn.moviemax.presentation.seemore.SeeMoreActivity
import com.lynn.moviemax.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private val nowPlayingAdapter: MovieAdapter by lazy {
        MovieAdapter {
            showBottomSheetInfo(it)
        }
    }
    private val popularAdapter: MovieAdapter by lazy {
        MovieAdapter {
            showBottomSheetInfo(it)
        }
    }
    private val upcomingAdapter: MovieAdapter by lazy {
        MovieAdapter {
            showBottomSheetInfo(it)
        }
    }
    private val topRatedAdapter: MovieAdapter by lazy {
        MovieAdapter {
            showBottomSheetInfo(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupOnclickSeeMore()
        getNowPlayingData()
        getPopularData()
        getUpcomingData()
        getTopRatedData()
        setupNowPlaying()
        setupPopular()
        setupUpcoming()
        setupTopRated()
    }

    private fun setupOnclickSeeMore() {
        binding.ivSmmNowplaying.setOnClickListener {
            SeeMoreActivity.startActivity(requireContext(), "Now Playing")
        }
        binding.ivSmmPopular.setOnClickListener {
            SeeMoreActivity.startActivity(requireContext(), "Popular")
        }
        binding.ivSmmToprated.setOnClickListener {
            SeeMoreActivity.startActivity(requireContext(), "Top Rated")
        }
        binding.ivSmmUpcomingmovie.setOnClickListener {
            SeeMoreActivity.startActivity(requireContext(), "Up Coming")
        }
    }

    private fun bindNowPlaying(data: List<Movie>) {
        nowPlayingAdapter.submitData(data)
    }

    private fun bindPopular(data: List<Movie>) {
        popularAdapter.submitData(data)
    }

    private fun bindUpcoming(data: List<Movie>) {
        upcomingAdapter.submitData(data)
    }

    private fun bindTopRated(data: List<Movie>) {
        topRatedAdapter.submitData(data)
    }

    private fun setupNowPlaying() {
        binding.rvItemNowPlaying.apply {
            adapter = nowPlayingAdapter
        }
    }

    private fun setupPopular() {
        binding.rvItemPopular.apply {
            adapter = popularAdapter
        }
    }

    private fun setupUpcoming() {
        binding.rvItemUpcoming.apply {
            adapter = upcomingAdapter
        }
    }

    private fun setupTopRated() {
        binding.rvItemTopRated.apply {
            adapter = topRatedAdapter
        }
    }

    private fun getNowPlayingData() {
        viewModel.getDataNowPlaying().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindNowPlaying(data)
                        setupBanner(data)
                    }
                },
            )
        }
    }

    private fun getPopularData() {
        viewModel.getDataPopular().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindPopular(data)
                    }
                },
            )
        }
    }

    private fun getUpcomingData() {
        viewModel.getDataUpcoming().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindUpcoming(data)
                    }
                },
            )
        }
    }

    private fun getTopRatedData() {
        viewModel.getDataTopRated().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindTopRated(data)
                    }
                },
            )
        }
    }

    private fun bindBanner(movie: Movie) {
        binding.layoutBanner.ivPosterMovie.load(movie.posterPath)
        binding.layoutBanner.tvMovieName.text = movie.title
        binding.layoutBanner.tvMovieDesc.text = movie.overview
        binding.layoutBanner.ivInfo.setOnClickListener {
            showBottomSheetInfo(movie)
        }
        binding.layoutBanner.ivShare.setOnClickListener {
            showShareBottomSheet(movie)
        }
    }

    private fun setupBanner(movie: List<Movie>) {
        val randomIndex = Random.nextInt(0, movie.size)
        bindBanner(movie[randomIndex])
        Handler().postDelayed(7000) {
            setupBanner(movie)
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
