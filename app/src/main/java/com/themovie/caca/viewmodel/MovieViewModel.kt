package com.themovie.caca.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.themovie.caca.R
import com.themovie.caca.model.Company
import com.themovie.caca.model.Genre
import com.themovie.caca.repository.MovieRepository
import com.themovie.caca.util.handleNetworkError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


sealed class MainState
data class GenreErrorState(val errorMessage: String) : MainState()
data class GenresLoadedState(val genres: List<Genre>) : MainState()

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val genreList: MutableList<Genre> = mutableListOf()
    private val compositeDisposable = CompositeDisposable()
    val mainState = MutableLiveData<MainState>()
    val loadingState = MutableLiveData<Boolean>()
    val company: Company by lazy {
        Company(
            companyName = "Marvel Entertainment",
            email = "marvel@entertainment.com",
            country = "United States",
            followers = 9_850,
            following = 985,
            logo = R.drawable.marvel,
            backdrop = R.drawable.marvel_backdrop
        )
    }

    init {
        loadGenres()
    }

    private fun loadGenres() {
        updateLoadingState(true)

        compositeDisposable.add(
            movieRepository.getGenres()
                .handleNetworkError()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGenresReceived, this::onError)
        )
    }

    private fun onGenresReceived(genres: List<Genre>) {
        genreList.addAll(genres)
        updateLoadingState(false)
        mainState.value = GenresLoadedState(genreList)
    }

    private fun onError(e: Throwable) {
        updateLoadingState(false)
        mainState.value = GenreErrorState(e.localizedMessage)
    }

    private fun updateLoadingState(state: Boolean) {
        loadingState.value = state
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}