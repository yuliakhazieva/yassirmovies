package com.example.yassirmovies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yassirmovies.R
import com.example.yassirmovies.databinding.MovieListFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment(R.layout.movie_list_fragment) {

    private val viewModel by viewModel<MovieListViewModel>()
    private lateinit var navController: NavController
    private var _binding: MovieListFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MoviesAdapter(::navigateToDetail)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()

        fetchData()
        viewModel.error.observe(this, {
            binding.swipeRefreshLayout.isRefreshing = false
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
        })

        val recycler = binding.moviesRecycler
        recycler.adapter = adapter
        recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.swipeRefreshLayout.setOnRefreshListener { fetchData() }
        viewModel.getMovieList()
    }

    private fun fetchData() {
        viewModel.getMovieList().subscribe {
            adapter.submitData(lifecycle, it)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun navigateToDetail(id: Int) {
        navController.navigate(
            MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(id)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}