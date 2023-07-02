package com.example.movie.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.movie.core.Constants
import com.example.movie.data.model.Movie
import com.example.movie.databinding.ContentSearchMovieItemBinding

class SearchMovieAdapter(
    private val context: Context,
    private var movies: ArrayList<Movie>,
) : RecyclerView.Adapter<SearchMovieAdapter.SearchMovieViewHolder>() {

    var onClickMovieDetail: ((movie: Movie) -> Unit)? = null

    inner class SearchMovieViewHolder(
        private val binding: ContentSearchMovieItemBinding,
        private val onClickMovieDetail: ((movie: Movie) -> Unit)?,
    ) : ViewHolder(binding.root) {

        fun onBind(movie: Movie) {
            binding.tvTitle.text = movie.title
            movie.posterPath?.let {
                Glide.with(context)
                    .load(Constants.BASE_URL_IMAGE + movie.posterPath.trim())
                    .into(binding.ivImages);
            }

            binding.cvMovie.setOnClickListener {
                onClickMovieDetail?.invoke(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {
        return SearchMovieViewHolder(
            ContentSearchMovieItemBinding.inflate(LayoutInflater.from(context), parent, false),
            onClickMovieDetail = onClickMovieDetail
        )
    }

    fun updateList(movies : List<Movie>){
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        holder.onBind(movies[position])
    }
}