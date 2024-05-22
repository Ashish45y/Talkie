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
    private val movieList: ArrayList<MoviesDto>
) :
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {
    private val handler = Handler(Looper.getMainLooper())
    private val autoSlideRunnable = Runnable {
        //viewPager2.currentItem = (viewPager2.currentItem + 1) % itemCount
    }

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

    fun startAutoSlide() {
        handler.postDelayed(autoSlideRunnable, AUTO_SLIDE_INTERVAL)
    }

    fun stopAutoSlide() {
        handler.removeCallbacks(autoSlideRunnable)
    }

//    fun attachToViewPager() {
//        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                startAutoSlide()
//            }
//        })
//    }

    companion object {
        const val AUTO_SLIDE_INTERVAL = 3000L
    }
}