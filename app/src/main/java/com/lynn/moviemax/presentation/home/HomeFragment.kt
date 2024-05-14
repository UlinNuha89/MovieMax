package com.lynn.moviemax.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lynn.moviemax.databinding.FragmentHomeBinding
import com.lynn.moviemax.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeader()
    }

    private fun setupHeader() {
        viewModel.getDataNowPlaying().observe(viewLifecycleOwner){
            it.proceedWhen (
                doOnSuccess = {
                    it.payload?.let {data ->
                        binding.tvExample.text = data[0].title
                    }
                },
                doOnLoading = {}
            )
        }
    }

}