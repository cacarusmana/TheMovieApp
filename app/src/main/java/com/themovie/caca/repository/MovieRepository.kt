package com.themovie.caca.repository

import com.themovie.caca.BuildConfig
import com.themovie.caca.model.Genre
import com.themovie.caca.model.response.MovieResponse
import com.themovie.caca.network.AppServices
import io.reactivex.Single

class MovieRepository(private val appServices: AppServices) {

    private val apiKey = BuildConfig.API_KEY


    fun getNowPlayingMovies(page: Int): Single<MovieResponse> {
        return appServices.getNowPlayingMovies(apiKey, page)
    }

    fun getPopularMovies(page: Int): Single<MovieResponse> {
        return appServices.getPopularMovies(apiKey, page)
    }

    fun getGenres(): Single<List<Genre>> {
        return appServices.getGenres(apiKey)
            .toObservable()
            .flatMapIterable { it.genres }
            .toList()
    }

}