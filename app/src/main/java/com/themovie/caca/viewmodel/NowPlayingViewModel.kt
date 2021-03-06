package com.themovie.caca.viewmodel

import com.themovie.caca.repository.MovieRepository
import com.themovie.caca.util.handleNetworkError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NowPlayingViewModel(private val movieRepository: MovieRepository) : BaseMovieViewModel() {

    init {
        loadMovies()
    }

    override fun loadMovies() {
        if (page == 0)
            updateLoadingState(true)
        else
            updateLoadMoreLoadingState(true)

        compositeDisposable.add(
            movieRepository.getNowPlayingMovies(++page)
                .handleNetworkError()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMovieReceived, this::onError)
        )
    }
}