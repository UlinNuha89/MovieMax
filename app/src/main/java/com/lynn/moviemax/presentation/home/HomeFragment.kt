package com.lynn.moviemax.presentation.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import coil.load
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.databinding.FragmentHomeBinding
import com.lynn.moviemax.presentation.home.adapter.MovieAdapter
import com.lynn.moviemax.presentation.seemore.SeeMoreActivity
import com.lynn.moviemax.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private val nowPlayingAdapter: MovieAdapter by lazy {
        MovieAdapter{
            Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
        }
    }
    private val popularAdapter: MovieAdapter by lazy {
        MovieAdapter{
            Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
        }
    }
    private val upcomingAdapter: MovieAdapter by lazy {
        MovieAdapter{
            Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
        }
    }
    private val topRatedAdapter: MovieAdapter by lazy {
        MovieAdapter{
            Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
            SeeMoreActivity.startActivity(requireContext(),"Now Playing")
        }
        binding.ivSmmPopular.setOnClickListener {
            SeeMoreActivity.startActivity(requireContext(),"Popular")
        }
        binding.ivSmmToprated.setOnClickListener {
            SeeMoreActivity.startActivity(requireContext(),"Top Rated")
        }
        binding.ivSmmUpcomingmovie.setOnClickListener {
            SeeMoreActivity.startActivity(requireContext(),"Up Coming")
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

    private fun bindBanner(number: Int, movie: List<Movie>) {
        binding.layoutBanner.ivPosterMovie.load(movie[number].posterPath)
        binding.layoutBanner.tvMovieName.text = movie[number].title
        binding.layoutBanner.tvMovieDesc.text = movie[number].overview

    }

    private fun setupBanner(movie: List<Movie>) {
        bindBanner(Random.nextInt(0, movie.size), movie)
        Handler().postDelayed(7000) {
            setupBanner(movie)
        }
    }
}