package com.themovie.caca.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.themovie.caca.R
import com.themovie.caca.databinding.ItemGenreBinding
import com.themovie.caca.model.Genre

class GenreAdapter(
    private val genres: MutableList<Genre>,
    private val listener: (Genre) -> Unit
) : RecyclerView.Adapter<GenreAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemGenreBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_genre, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.genre = genres[position]
        holder.binding.root.setOnClickListener { listener(genres[position]) }
    }

    fun notifyChanges(genres: List<Genre>) {
        this.genres.addAll(genres)
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root)
}