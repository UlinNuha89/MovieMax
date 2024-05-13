package com.lynn.moviemax.presentation.mylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lynn.moviemax.databinding.FragmentMylistBinding

class MylistFragment : Fragment() {
    private lateinit var binding: FragmentMylistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMylistBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}