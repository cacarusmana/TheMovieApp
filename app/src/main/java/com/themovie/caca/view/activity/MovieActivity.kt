package com.themovie.caca.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.themovie.caca.R
import com.themovie.caca.databinding.ActivityMovieBinding
import com.themovie.caca.model.ViewPagerModel
import com.themovie.caca.util.gone
import com.themovie.caca.util.toast
import com.themovie.caca.view.BaseView
import com.themovie.caca.view.adapter.GenreAdapter
import com.themovie.caca.view.adapter.ViewPagerAdapter
import com.themovie.caca.view.fragment.NowPlayingFragment
import com.themovie.caca.view.fragment.PopularFragment
import com.themovie.caca.viewmodel.GenreErrorState
import com.themovie.caca.viewmodel.GenresLoadedState
import com.themovie.caca.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieActivity : AppCompatActivity(), BaseView {

    private val mainViewModel: MovieViewModel by viewModel()

    private val genreAdapter by lazy {
        GenreAdapter(mutableListOf()) {
            toast(it.name)
        }
    }

    private lateinit var dataBinding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie)

        initCollapsingToolbar()
        initComponent()
        initObserver()
    }

    private fun initCollapsingToolbar() {
        setSupportActionBar(toolbar)
        collapsingToolbar.title = " "
        appbar.setExpanded(true)

        // hiding & showing the title when toolbar expanded & collapsed
        appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = getString(R.string.app_name)
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })

    }

    override fun initComponent() {
        dataBinding.viewModel = mainViewModel

        rvGenres.apply {
            layoutManager = LinearLayoutManager(this@MovieActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = genreAdapter
        }


        val viewPagerModels = arrayListOf<ViewPagerModel>().apply {
            add(ViewPagerModel(NowPlayingFragment(), getString(R.string.now_playing)))
            add(ViewPagerModel(PopularFragment(), getString(R.string.popular)))
        }

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, viewPagerModels)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun initObserver() {
        mainViewModel.loadingState.observe(this, Observer {
            layout_shimmer.apply {
                if (it) {
                    startShimmerAnimation()
                } else {
                    stopShimmerAnimation()
                    gone()
                }
            }
        })

        mainViewModel.mainState.observe(this, Observer { state ->
            when (state) {
                is GenresLoadedState -> {
                    genreAdapter.notifyChanges(state.genres)
                }
                is GenreErrorState -> {
                    toast(state.errorMessage)
                }
            }
        })

    }

}
