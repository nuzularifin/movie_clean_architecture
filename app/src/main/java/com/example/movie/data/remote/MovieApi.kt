package com.example.movie.data.remote

import com.example.movie.core.Constants
import com.example.movie.data.model.AuthenticationResponse
import com.example.movie.data.model.Movie
import com.example.movie.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("/3/authentication/guest_session/new")
    suspend fun getAuthentication(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ) : Response<AuthenticationResponse>

    @GET("3/movie/popular")
    suspend fun fetchPopularMovie(
        @Header("Authorization") auth: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = "en-US"
    ) : Response<MovieResponse>

    @GET("3/movie/upcoming")
    suspend fun fetchTopRatedMovies(
        @Query("page") page:Int,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = "en-US"
    ) : Response<MovieResponse>

    @GET("/3/discover/movie")
    suspend fun fetchActionMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("with_genres") with_genres:Int,
    ) : Response<MovieResponse>

    @GET("/3/search/movie")
    suspend fun fetchSearchMovie(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = Constants.API_KEY,
    ) : Response<MovieResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun fetchMovieDetail(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = "en-US",
    ) : Response<Movie>
}