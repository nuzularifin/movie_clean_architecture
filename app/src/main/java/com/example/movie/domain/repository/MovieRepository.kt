package com.example.movie.domain.repository

import com.example.movie.data.model.AuthenticationResponse
import com.example.movie.data.model.Movie
import com.example.movie.data.model.MovieResponse

interface MovieRepository {

    suspend fun fetchMovies(page: Int) : MovieResponse?

    suspend fun fetchAuthentication() : AuthenticationResponse?

    suspend fun fetchLatestMovie(page: Int) : MovieResponse?

    suspend fun fetchActionMovie(withGenreId: Int) : MovieResponse?

    suspend fun fetchSearchMovie(query: String, page: Int) : MovieResponse?

    suspend fun fetchMovieDetail(movieId: Long) : Movie?
}