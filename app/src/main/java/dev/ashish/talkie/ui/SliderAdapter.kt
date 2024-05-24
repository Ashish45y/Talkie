package dev.ashish.talkie.ui

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import dev.ashish.talkie.data.model.MoviesDto
import dev.ashish.talkie.databinding.SlideItemContainerBinding

class SliderAdapter(
    private val movieList: ArrayList<MoviesDto>,
) :
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {


    class SliderViewHolder(private val binding: SlideItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(moviesDto: MoviesDto) {
            binding.titleTextView.text = moviesDto.title
            Glide.with(binding.imageViewSlider.context)
                .load(moviesDto.getFullPosterPath())
                .into(binding.imageViewSlider)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            SlideItemContainerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = movieList.size


    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    fun getTotalItem():Int = movieList.size


    fun setData(list :List<MoviesDto>) {
        movieList.addAll(list)
        notifyDataSetChanged()
    }

    companion object {
        const val AUTO_SLIDE_INTERVAL = 1000L
    }
}