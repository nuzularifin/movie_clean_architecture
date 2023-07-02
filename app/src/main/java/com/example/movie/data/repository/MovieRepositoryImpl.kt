package com.example.movie.data.repository

import com.example.movie.core.Constants
import com.example.movie.data.model.AuthenticationResponse
import com.example.movie.data.model.Movie
import com.example.movie.data.model.MovieResponse
import com.example.movie.data.remote.MovieApi
import com.example.movie.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val moviesAPI: MovieApi
) : MovieRepository {

    override suspend fun fetchMovies(page: Int): MovieResponse? {
        val result = moviesAPI.fetchPopularMovie(
            page = page,
            auth = "Bearer ${Constants.API_KEY}",
        )
        return result.body()
    }

    override suspend fun fetchAuthentication(): AuthenticationResponse? {
        val result = moviesAPI.getAuthentication()
        return result.body()
    }

    override suspend fun fetchLatestMovie(page: Int): MovieResponse? {
        val result = moviesAPI.fetchTopRatedMovies(
            page = page
        )
        return result.body()
    }

    override suspend fun fetchActionMovie(withGenreId: Int): MovieResponse? {
        val result = moviesAPI.fetchActionMovies(
            with_genres = withGenreId
        )
        return result.body()
    }

    override suspend fun fetchSearchMovie(query: String, page: Int): MovieResponse? {
        val result = moviesAPI.fetchSearchMovie(
            page = page,
            query = query,
            includeAdult = false
        )
        return result.body()
    }

    override suspend fun fetchMovieDetail(movieId: Long): Movie? {
        val result = moviesAPI.fetchMovieDetail(
            movieId = movieId
        )
        return result.body()
    }
}