package com.example.yassirmovies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.yassirmovies.R
import com.example.yassirmovies.databinding.MovieDetailFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment(R.layout.movie_list_fragment) {

    private val viewModel by viewModel<MovieDetailViewModel>()
    private val args: MovieDetailFragmentArgs by navArgs()
    private lateinit var navController: NavController
    private var _binding: MovieDetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navController = view.findNavController()

        viewModel.movie.observe(this, {
            binding.name.text = it.name
            binding.year.text = it.year
            binding.description.text = it.description
            Glide
                .with(requireContext())
                .load(it.image)
                .into(binding.poster)
        })
        viewModel.error.observe(this, {
            if (it) {
                val builder = AlertDialog.Builder(requireContext())
                with(builder)
                {
                    setTitle("Error!")
                    setMessage("Something is wrong, try again later")
                    setPositiveButton("Ok", null)
                    show()
                }
            }
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.isVisible = false
        })

        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.getMovieDetails(args.id) }
        viewModel.getMovieDetails(args.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}