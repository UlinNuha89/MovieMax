package com.lynn.moviemax.presentation.seemore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lynn.moviemax.databinding.ActivitySeeMoreBinding
import com.lynn.moviemax.presentation.seemore.adapter.SeeMoreAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SeeMoreActivity : AppCompatActivity() {
    private val binding: ActivitySeeMoreBinding by lazy {
        ActivitySeeMoreBinding.inflate(layoutInflater)
    }
    private val viewModel: SeeMoreViewModel by viewModel{
        parametersOf(intent.extras)
    }
    private val seeMoreAdapter: SeeMoreAdapter by lazy {
        SeeMoreAdapter()
    }

    companion object {
        const val EXTRAS_ITEM = "EXTRAS_ITEM"
        fun startActivity(context: Context, category:String) {
            val intent = Intent(context, SeeMoreActivity::class.java)
            intent.putExtra(EXTRAS_ITEM, category)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpAdapter()
        observeData()
    }

    private fun observeData() {
        viewModel.header.let {
            binding.tvHeader.text = it
            when(it){
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
}