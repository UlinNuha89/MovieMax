package com.lynn.moviemax.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.databinding.FragmentHomeBinding
import com.lynn.moviemax.presentation.home.adapter.NowPlayingAdapter
import com.lynn.moviemax.presentation.home.adapter.PopularAdapter
import com.lynn.moviemax.presentation.home.adapter.TopRatedAdapter
import com.lynn.moviemax.presentation.home.adapter.UpcomingAdapter
import com.lynn.moviemax.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private val nowPlayingAdapter: NowPlayingAdapter by lazy {
        NowPlayingAdapter()
    }
    private val popularAdapter: PopularAdapter by lazy {
        PopularAdapter()
    }
    private val upcomingAdapter: UpcomingAdapter by lazy {
        UpcomingAdapter()
    }
    private val topRatedAdapter: TopRatedAdapter by lazy {
        TopRatedAdapter()
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
        //setupHeader()
        getNowPlayingData()
        getPopularData()
        getUpcomingData()
        getTopRatedData()
        setupNowPlaying()
        setupPopular()
        setupUpcoming()
        setupTopRated()
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

    /*    private fun setupHeader() {
            viewModel.getDataNowPlaying().observe(viewLifecycleOwner) {
                it.proceedWhen(
                    doOnSuccess = {
                        it.payload?.let { data ->
                            // Tambahkan kode untuk menampilkan data pada header di sini
                        }
                    },
                    doOnLoading = {}
                )
            }
        }*/
}