package com.example.movie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.core.extentions.toLiveData
import com.example.movie.core.mapper.toAuthentication
import com.example.movie.data.model.Authentication
import com.example.movie.data.model.Movie
import com.example.movie.data.model.Review
import com.example.movie.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList = _movieList.toLiveData()

    private val _latestMovieList = MutableLiveData<List<Movie>>()
    val latestMovieList = _latestMovieList.toLiveData()

    private val _actionMovieList = MutableLiveData<List<Movie>>()
    val actionMovieList = _actionMovieList.toLiveData()

    private val _searchMovieList = MutableLiveData<List<Movie>>()
    val searchMovieList = _searchMovieList.toLiveData()

    private val _searchMovieListLoadMore = MutableLiveData<List<Movie>>()
    val searchMovieListLoadMore = _searchMovieListLoadMore.toLiveData()

    private val _detailMovie = MutableLiveData<Movie>()
    val detailMovie = _detailMovie.toLiveData()

    private val _movieReviews = MutableLiveData<List<Review>>()
    val movieReview = _movieReviews.toLiveData()

    private val _auth = MutableLiveData<Authentication>()
    val auth = _auth.toLiveData()

    fun fetchAuthentication() {
        viewModelScope.launch {
            try {
                val result = movieRepository.fetchAuthentication()
                _auth.postValue(result?.toAuthentication())
            } catch (e: Exception) {
                Log.e("error", "Authentication: ${e.printStackTrace()}")
            }
        }
    }

    fun fetchMovies(page: Int) {
        viewModelScope.launch {
            try {
                val result = movieRepository.fetchMovies(page)
                _movieList.postValue(result?.movie)
            } catch (e: Exception) {
                Log.e("error", "fetchMovies: ${e.printStackTrace()}")
            }
        }
    }

    fun fetchLatestMovie(page: Int) {
        viewModelScope.launch {
            try {
                val result = movieRepository.fetchLatestMovie(page)
                _latestMovieList.postValue(result?.movie)
            } catch (e: Exception) {
                Log.e("error", "latest: ${e.printStackTrace()}")
            }
        }
    }

    fun fetchActionMovies(withGenre: Int) {
        viewModelScope.launch {
            try {
                val result = movieRepository.fetchActionMovie(withGenre)
                _actionMovieList.postValue(result?.movie)
            } catch (e: Exception) {
                Log.e("error", "action_movies: ${e.printStackTrace()}")
            }
        }
    }

    fun fetchSearchMovie(page: Int, query: String) {
        if (query.isEmpty()) {
            _searchMovieList.postValue(emptyList())
        } else {
            viewModelScope.launch {
                try {
                    val result = movieRepository.fetchSearchMovie(query, page)
                    _searchMovieList.postValue(result?.movie)
                } catch (e: Exception) {
                    Log.e("error", "search_movie: ${e.printStackTrace()}")
                }
            }
        }
    }

    fun fetchDetailMovie(id: Long) {
        viewModelScope.launch {
            try {
                val result = movieRepository.fetchMovieDetail(movieId = id)
                _detailMovie.postValue(result ?: null)
            } catch (e: Exception) {
                Log.e("error", "details_movie: ${e.printStackTrace()}")
            }
        }
    }

    fun loadMoreData(page: Int, query: String) {
        viewModelScope.launch {
            try {
                Log.d("LoadMore", "loadMoreData: page -> $page")
                val result = movieRepository.fetchSearchMovie(query, page)
                _searchMovieListLoadMore.postValue(result?.movie)
            } catch (e: Exception) {
                Log.e("error", "search_movie: ${e.printStackTrace()}")
            }
        }
    }

    fun fetchReviewsMovie(page: Int, movieId: Long){
        viewModelScope.launch {
            try {
                val result = movieRepository.fetchMovieReviews(movieId = movieId, page = page)
                _movieReviews.postValue(result?.movie)
            } catch (e: Exception) {
                Log.e("error", "details_movie: ${e.printStackTrace()}")
            }
        }
    }
}