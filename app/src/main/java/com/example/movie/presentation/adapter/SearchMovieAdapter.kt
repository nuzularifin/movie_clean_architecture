package com.example.movie.presentation.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.movie.core.Constants
import com.example.movie.data.model.Movie
import com.example.movie.databinding.ContentProgressBarBinding
import com.example.movie.databinding.ContentSearchMovieItemBinding

class SearchMovieAdapter(
    private val context: Context,
    private var movies: MutableList<Movie?> = ArrayList(),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onClickMovieDetail: ((movie: Movie) -> Unit)? = null

    inner class LoadingViewHolder(
        private val binding: ContentProgressBarBinding,
    ) : ViewHolder(binding.root) {
    }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constants.VIEW_TYPE_ITEM){
            SearchMovieViewHolder(
                ContentSearchMovieItemBinding.inflate(LayoutInflater.from(context), parent, false),
                onClickMovieDetail = onClickMovieDetail
            )
        } else {
            LoadingViewHolder(
                ContentProgressBarBinding.inflate(LayoutInflater.from(context), parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (movies[position] == null){
            Constants.VIEW_TYPE_LOADING
        } else {
            Constants.VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.itemViewType == Constants.VIEW_TYPE_ITEM){
            movies[position]?.let { (holder as SearchMovieViewHolder).onBind(it) }
        }
    }

    fun removeAllItems(){
        this.movies.clear()
        notifyDataSetChanged()
    }
    fun addingItems(movies: List<Movie>){
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = movies.size

    fun addLoadingView(){
        Handler().post {
            this.movies.add(null)
            notifyItemInserted(this.movies.size - 1)
        }
    }

    fun removeLoadingView(){
        if (this.movies.size != 0){
            this.movies.removeAt(this.movies.size - 1)
            notifyItemRemoved(this.movies.size)
        }
    }

    fun getItemAtPosition(position: Int): Movie? {
        return movies[position]
    }
}