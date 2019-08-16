package com.themovie.caca.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.themovie.caca.R
import com.themovie.caca.databinding.ItemMovieBinding
import com.themovie.caca.model.Movie

class MovieAdapter(
    private val movies: MutableList<Movie>,
    private val listener: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemMovieBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_movie, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.movie = movies[position]
        holder.binding.imgThumbnail.setOnClickListener { listener(movies[position]) }
    }

    fun notifyChanges(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)
}