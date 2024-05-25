package dev.ashish.talkie.ui.adapater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.ashish.talkie.data.model.MoviesDto
import dev.ashish.talkie.databinding.UpcomingMovieItemBinding

class UpcomingMovieAdapter(private val upcomingMovieMovieList: ArrayList<MoviesDto>) :
    RecyclerView.Adapter<UpcomingMovieAdapter.UpcomingMovieViewHolder>() {
    class UpcomingMovieViewHolder(private val binding: UpcomingMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(moviesDto: MoviesDto) {
            binding.titleText.text = moviesDto.title
            Glide.with(binding.moviePoster.context)
                .load(moviesDto.getFullPosterPath())
                .into(binding.moviePoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMovieViewHolder {
        return UpcomingMovieViewHolder(
            UpcomingMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = upcomingMovieMovieList.size

    override fun onBindViewHolder(holder: UpcomingMovieViewHolder, position: Int) {
        holder.bind(upcomingMovieMovieList[position])
    }

    fun setData(list: List<MoviesDto>) {
        upcomingMovieMovieList.addAll(list)
        notifyDataSetChanged()
    }
}