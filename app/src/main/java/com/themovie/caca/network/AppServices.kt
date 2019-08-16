package com.themovie.caca.network

import com.themovie.caca.model.response.GenreResponse
import com.themovie.caca.model.response.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AppServices {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apiKey: String
    ): Single<GenreResponse>
}