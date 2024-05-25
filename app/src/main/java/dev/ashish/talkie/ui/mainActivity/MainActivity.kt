package dev.ashish.talkie.ui.mainActivity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import dev.ashish.talkie.TalkieApplication
import dev.ashish.talkie.databinding.ActivityMainBinding
import dev.ashish.talkie.di.component.DaggerActivityComponent
import dev.ashish.talkie.di.module.ActivityModule
import dev.ashish.talkie.ui.adapater.PopularMovieAdapter
import dev.ashish.talkie.ui.adapater.SliderAdapter
import dev.ashish.talkie.ui.adapater.UpcomingMovieAdapter
import dev.ashish.talkie.ui.base.UiState
import dev.ashish.talkie.ui.viewmodel.NowPlayingViewModel
import dev.ashish.talkie.ui.viewmodel.PopularMovieViewModel
import dev.ashish.talkie.ui.viewmodel.UpcomingMovieViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: NowPlayingViewModel

    @Inject
    lateinit var popularViewModel: PopularMovieViewModel

    @Inject
    lateinit var upcomingViewmodel: UpcomingMovieViewModel

    @Inject
    lateinit var adapter: SliderAdapter

    @Inject
    lateinit var adapterPopular: PopularMovieAdapter

    @Inject
    lateinit var adapaterUpcoming: UpcomingMovieAdapter

    private val slideHandler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        injectDependencies()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUi()
        setupObserver()
        setupPopularMovie()
        setupUpcomingMovie()
    }

    private fun setupUpcomingMovie() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                upcomingViewmodel.upcomingUiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            adapaterUpcoming.setData(it.data)
                        }

                        is UiState.Loading -> {
                            Log.d("Rai", "setupUpcomingMovie: ")
                        }

                        is UiState.Error -> {
                            Log.d("Rai", "setupUpcomingMovie: Error")
                        }
                    }
                }
            }
        }
    }

    private fun setupPopularMovie() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                popularViewModel.popularMovieUiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            adapterPopular.setData(it.data)
                        }

                        is UiState.Loading -> {

                        }

                        is UiState.Error -> {

                        }
                    }
                }
            }
        }
    }

    private fun setUpUi() {
        binding.viewPagger.adapter = adapter
        binding.recViewPopular.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterPopular
        }

        binding.recViewUpcomming.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapaterUpcoming
        }
    }

    private val handler = Handler(Looper.getMainLooper())
    fun attachToViewPager() {
        binding.viewPagger.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                startAutoSlide()
            }
        })
    }

    private val autoSlideRunnable = Runnable {
        binding.viewPagger.currentItem =
            (binding.viewPagger.currentItem + 1) % adapter.getTotalItem()
    }

    fun startAutoSlide() {
        handler.postDelayed(autoSlideRunnable, SliderAdapter.AUTO_SLIDE_INTERVAL)
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.nowPlayingUiState.collect {
                    when (it) {
                        is dev.ashish.talkie.ui.base.UiState.Success -> {
                            adapter.setData(it.data)
                            binding.viewPagger.clipToPadding = false
                            binding.viewPagger.clipChildren = false
                            binding.viewPagger.offscreenPageLimit = 3
                            binding.viewPagger.getChildAt(0).overScrollMode =
                                RecyclerView.OVER_SCROLL_NEVER

                            val compositePageTransformer = CompositePageTransformer()
                            compositePageTransformer.addTransformer(MarginPageTransformer(48))
                            compositePageTransformer.addTransformer { page, position ->
                                val r = 1 - Math.abs(position)
                                page.scaleY = 0.85f + r * 0.15f
                            }
                            binding.viewPagger.setPageTransformer(compositePageTransformer)

                            binding.viewPagger.setCurrentItem(1, false)

                            binding.viewPagger.registerOnPageChangeCallback(object :
                                ViewPager2.OnPageChangeCallback() {
                                override fun onPageSelected(position: Int) {
                                    super.onPageSelected(position)
                                    slideHandler.removeCallbacksAndMessages(null)
                                    slideHandler.postDelayed(
                                        sliderRunnable,
                                        SliderAdapter.AUTO_SLIDE_INTERVAL
                                    )
                                }
                            })
                            startAutoSlide()
                        }

                        is dev.ashish.talkie.ui.base.UiState.Loading -> {
                            Log.d("Rahul", "setupObserver: Loading")
                        }

                        is dev.ashish.talkie.ui.base.UiState.Error -> {
                            Log.d("Rahul", "setupObserver: Error")
                        }
                    }
                }
            }
        }
    }

    private val sliderRunnable = Runnable {
        binding.viewPagger.setCurrentItem(binding.viewPagger.currentItem + 1, true)
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as TalkieApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}