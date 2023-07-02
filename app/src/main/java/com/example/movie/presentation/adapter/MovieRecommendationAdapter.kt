package com.example.movie.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.movie.core.Constants
import com.example.movie.data.model.Movie
import com.example.movie.databinding.ContentMovieItemBinding

class MovieRecommendationAdapter(
    private val context: Context,
    private val movies: List<Movie>,
) : RecyclerView.Adapter<MovieRecommendationAdapter.SliderViewHolder>() {

    var OnClickMovieDetail: ((movie: Movie) -> Unit)? = null

    inner class SliderViewHolder(
        private val binding: ContentMovieItemBinding,
        private val onClickMovieDetail: ((movie: Movie)-> Unit)?,
    ) : ViewHolder(binding.root) {

        fun onBind(movie: Movie) {

            binding.tvTitle.text = movie.title
            binding.tvDescription.text = movie.overview

            movie.posterPath?.let {
                Glide.with(context)
                    .load(Constants.BASE_URL_IMAGE + it.trim())
                    .into(binding.ivImages);
            }

            binding.cvImages.setOnClickListener {
                onClickMovieDetail?.invoke(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            ContentMovieItemBinding.inflate(LayoutInflater.from(context), parent, false),
            onClickMovieDetail = OnClickMovieDetail
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.onBind(movies[position])
    }
}