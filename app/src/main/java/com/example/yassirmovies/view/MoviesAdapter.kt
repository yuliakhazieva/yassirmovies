package com.example.yassirmovies.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yassirmovies.R
import com.example.yassirmovies.domain.Movie

class MoviesAdapter(val toDetail: (position: Int) -> Unit) :
    PagingDataAdapter<Movie, RecyclerView.ViewHolder>(COMPARATOR){

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)

        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemView) {
            setOnClickListener {
                toDetail(getItem(position)!!.id)
            }
            Glide
                .with(context)
                .load(getItem(position)!!.image)
                .into(findViewById(R.id.poster))
            findViewById<TextView>(R.id.name).text = getItem(position)!!.name
            findViewById<TextView>(R.id.year).text = getItem(position)!!.year
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id
        }
    }
}