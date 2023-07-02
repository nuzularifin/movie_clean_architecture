package com.example.movie.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.movie.core.Constants
import com.example.movie.data.model.Movie
import com.example.movie.databinding.ContentLatestBinding

class LatestAdapter(
    private val context: Context,
    private val latestMovies: ArrayList<Movie>,
) : RecyclerView.Adapter<LatestAdapter.LatestMovieViewHolder>() {

    var onClickMovieDetail: ((movie: Movie)-> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestMovieViewHolder {
        return LatestMovieViewHolder(
            ContentLatestBinding.inflate(LayoutInflater.from(context), parent, false),
            onClickMovieDetail = onClickMovieDetail
        )
    }

    override fun getItemCount(): Int = latestMovies.size

    override fun onBindViewHolder(holder: LatestMovieViewHolder, position: Int) {
        holder.onBind(latestMovies[position])
    }

    inner class LatestMovieViewHolder(
        private val binding: ContentLatestBinding,
        private val onClickMovieDetail: ((movie: Movie)-> Unit)?,
    ) : ViewHolder(binding.root){

        fun onBind(movie: Movie) {
            binding.tvTitle.text = movie.title
            Glide.with(context)
                .load(Constants.BASE_URL_IMAGE + movie.posterPath?.trim())
                .into(binding.image);

            binding.cvMovie.setOnClickListener {
                onClickMovieDetail?.invoke(movie)
            }
        }
    }
}