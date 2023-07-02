package com.example.movie.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.movie.core.Constants
import com.example.movie.data.model.Movie
import com.example.movie.databinding.ContentActionMovieItemBinding

class ActionAdapter(
    val context: Context,
    private val movies: ArrayList<Movie>,
) : RecyclerView.Adapter<ActionAdapter.ActionViewHolder>() {

    var onClickMovieDetail: ((movie: Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        return ActionViewHolder(
            ContentActionMovieItemBinding.inflate(LayoutInflater.from(context), parent, false),
            onClickMovieDetail = onClickMovieDetail
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.onBind(movies[position])
    }

    inner class ActionViewHolder(
        private val binding: ContentActionMovieItemBinding,
        private val onClickMovieDetail: ((movie: Movie) -> Unit)?,
    ) : ViewHolder(binding.root) {

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