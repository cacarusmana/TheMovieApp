package com.themovie.caca.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.themovie.caca.model.Movie
import com.themovie.caca.model.response.MovieResponse
import io.reactivex.disposables.CompositeDisposable


sealed class MovieState
data class MovieErrorState(val errorMessage: String) : MovieState()
data class MovieLoadedState(val movies: List<Movie>) : MovieState()


abstract class BaseMovieViewModel : ViewModel() {

    var page = 0
    var totalPages = 1
    var isLoading = false

    private val movieList: MutableList<Movie> = mutableListOf()
    protected val compositeDisposable = CompositeDisposable()
    val movieState = MutableLiveData<MovieState>()
    val loadingState = MutableLiveData<Boolean>()
    val loadMoreLoadingState = MutableLiveData<Boolean>()


    abstract fun loadMovies()

    protected fun onMovieReceived(response: MovieResponse) {
        if (page == 1) totalPages = response.totalPages

        movieList.addAll(response.results)
        updateLoadingState(false)
        movieState.value = MovieLoadedState(movieList)
    }

    protected fun onError(e: Throwable) {
        page--
        updateLoadingState(false)
        movieState.value = MovieErrorState(e.localizedMessage)
    }

    protected fun updateLoadingState(state: Boolean) {
        isLoading = state
        loadingState.value = isLoading

        if (!state) loadMoreLoadingState.value = state
    }

    protected fun updateLoadMoreLoadingState(state: Boolean) {
        isLoading = state
        loadMoreLoadingState.value = isLoading
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}