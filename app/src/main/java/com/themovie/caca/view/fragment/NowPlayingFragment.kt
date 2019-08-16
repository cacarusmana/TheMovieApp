package com.themovie.caca.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.themovie.caca.R
import com.themovie.caca.util.gone
import com.themovie.caca.util.toast
import com.themovie.caca.util.visible
import com.themovie.caca.view.BaseView
import com.themovie.caca.view.adapter.MovieAdapter
import com.themovie.caca.viewmodel.MovieErrorState
import com.themovie.caca.viewmodel.MovieLoadedState
import com.themovie.caca.viewmodel.NowPlayingViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NowPlayingFragment : Fragment(), BaseView {
    private val viewModel: NowPlayingViewModel by viewModel()

    private val movieAdapter by lazy {
        MovieAdapter(mutableListOf()) {
            toast(it.title)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponent()
        initObserver()
    }

    override fun initComponent() {
        rvMovies.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = movieAdapter
            setHasFixedSize(true)
            addOnScrollListener(endlessScrollListener)
        }
    }

    override fun initObserver() {
        viewModel.loadingState.observe(this, Observer {
            progressbar.apply {
                if (it) visible() else gone()
            }
        })

        viewModel.movieState.observe(this, Observer {
            when (it) {
                is MovieLoadedState -> {
                    movieAdapter.notifyChanges(it.movies)
                }
                is MovieErrorState -> {
                    toast(it.errorMessage)
                }
            }
        })
    }

    private val endlessScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (dy > 0) {
                val visibleThreshold = 2

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                val currentTotalCount = layoutManager.itemCount

                if (currentTotalCount <= lastItem + visibleThreshold) {

                    with(viewModel) {
                        if (page < totalPages && !isLoading) {
                            loadMovies()
                        }
                    }

                }
            }
        }
    }
}