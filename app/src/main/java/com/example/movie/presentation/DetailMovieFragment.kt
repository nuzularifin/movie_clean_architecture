package com.example.movie.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movie.core.Constants
import com.example.movie.core.extentions.gone
import com.example.movie.core.extentions.reObserve
import com.example.movie.core.extentions.showToast
import com.example.movie.core.extentions.visible
import com.example.movie.data.model.Movie
import com.example.movie.databinding.FragmentSecond2Binding
import com.example.movie.presentation.adapter.LatestAdapter
import com.example.movie.presentation.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class DetailMovieFragment : Fragment() {

    private var _binding: FragmentSecond2Binding? = null
    lateinit var latestAdapter: LatestAdapter
    private val viewModel: MovieViewModel by viewModels()
    private val latestMovies: ArrayList<Movie> = arrayListOf()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecond2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong("movie", 0)
        id?.let {
            requireContext().showToast("id : $id")
            viewModel.fetchDetailMovie(it)
            viewModel.fetchReviewsMovie(1, it)
        }

        val latestLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvLatest.layoutManager = latestLayoutManager
        latestAdapter = LatestAdapter(
            requireContext(),
            latestMovies
        )
        binding.rvLatest.adapter = latestAdapter

        setupObserver()

        viewModel.fetchLatestMovie(page = 1)

//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_Second2Fragment_to_First2Fragment)
//        }
    }

    private fun setupView(movie: Movie) {
        with(binding){
            tvTitle.text = movie.title
            tvDescription.text = movie.overview
            movie.posterPath?.let {
                Glide.with(requireContext())
                    .load(Constants.BASE_URL_IMAGE + movie.posterPath?.trim())
                    .into(imvMovie);
            }
        }
    }

    private fun setupObserver() {
        with(viewModel){
            detailMovie.reObserve(requireActivity()){ movie ->
                movie?.let {
                    setupView(movie)
                }
            }
            movieReview.reObserve(requireActivity()){
                with(binding){
                    if (it.isNotEmpty()){
                        llReview.visible()
                        val review = it.first()
                        val rating = if (review.authorDetails?.rating != null) "${review.authorDetails.rating}" else "0"
                        tvUsername.text = " ${review.authorDetails?.username}, rating : $rating"
                        tvContent.text = review.content
                    } else {
                        llReview.gone()
                    }
                }
            }
            latestMovieList.reObserve(requireActivity()){
                it.forEach { latest ->
                    latestMovies.add(latest)
                }
                latestAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}