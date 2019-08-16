package com.themovie.caca.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.themovie.caca.model.ViewPagerModel

class ViewPagerAdapter(
    private val fm: FragmentManager,
    private val viewPagerModels: List<ViewPagerModel>
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = viewPagerModels[position].fragment

    override fun getCount(): Int = viewPagerModels.size

    override fun getPageTitle(position: Int): CharSequence? = viewPagerModels[position].title

}