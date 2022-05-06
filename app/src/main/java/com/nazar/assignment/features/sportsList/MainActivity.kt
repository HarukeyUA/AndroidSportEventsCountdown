package com.nazar.assignment.features.sportsList

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.nazar.assignment.R
import com.nazar.assignment.databinding.ActivityMainBinding
import com.nazar.assignment.features.sportsList.list.SportsListAdapter
import com.nazar.assignment.features.sportsList.model.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: SportsViewModel by viewModels()

    private val adapter by lazy {
        SportsListAdapter(
            viewModel::toggleExpandedState,
            viewModel::toggleStarredState
        )
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sportsList.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.sportsFlow.collect {
                adapter.submitList(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.eventsFlow.collect {
                when (it) {
                    Event.HttpError -> showSnackBar(R.string.server_error)
                    Event.IOError -> showSnackBar(R.string.connection_error)
                    Event.UnknownError -> showSnackBar(R.string.unknown_error)
                }
            }
        }
    }

    private fun showSnackBar(@StringRes message: Int) {
        Snackbar
            .make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry) {
                viewModel.fetchSports()
            }.show()
    }
}
