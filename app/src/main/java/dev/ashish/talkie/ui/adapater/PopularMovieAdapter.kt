package dev.ashish.talkie.ui.adapater

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.ashish.talkie.data.model.MoviesDto
import dev.ashish.talkie.databinding.PopularMovieItemBinding

class PopularMovieAdapter(private val popularMovieList: ArrayList<MoviesDto>) :
    RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder>() {
    class PopularMovieViewHolder(private val binding: PopularMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(moviesDto: MoviesDto) {
            binding.titleText.text = moviesDto.title
            Glide.with(binding.moviePoster.context)
                .load(moviesDto.getFullPosterPath())
                .into(binding.moviePoster)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        return PopularMovieViewHolder(
            PopularMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = popularMovieList.size

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        holder.bind(popularMovieList[position])
    }

    fun setData(list: List<MoviesDto>) {
        popularMovieList.addAll(list)
        notifyDataSetChanged()
    }
}