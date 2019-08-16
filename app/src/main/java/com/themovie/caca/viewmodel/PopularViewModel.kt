package com.themovie.caca.viewmodel

import com.themovie.caca.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PopularViewModel(private val movieRepository: MovieRepository) : BaseMovieViewModel() {


    init {
        loadMovies()
    }

    override fun loadMovies() {
        updateLoadingState(true)

        compositeDisposable.add(
            movieRepository.getPopularMovies(++page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMovieReceived, this::onError)
        )
    }


}