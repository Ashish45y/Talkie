package dev.ashish.talkie.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import dev.ashish.talkie.R
import dev.ashish.talkie.TalkieApplication
import dev.ashish.talkie.databinding.ActivityMainBinding
import dev.ashish.talkie.di.component.DaggerActivityComponent
import dev.ashish.talkie.di.module.ActivityModule
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: NowPlayingViewModel

    @Inject
    lateinit var adapter: SliderAdapter
    private lateinit var viewPager: ViewPager2
    private val slideHandler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        injectDependencies()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPager = binding.viewPagger
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupObserver()
        setupUI()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.nowPlayingUiState.collect {
                    when (it) {
                        is dev.ashish.talkie.ui.base.UiState.Success -> {
                            adapter = SliderAdapter(it.data, viewPager)
                            viewPager.adapter = adapter
                            adapter.attachToViewPager()

                            viewPager.clipToPadding = false
                            viewPager.clipChildren = false
                            viewPager.offscreenPageLimit = 3
                            viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

                            val compositePageTransformer = CompositePageTransformer()
                            compositePageTransformer.addTransformer(MarginPageTransformer(48))
                            compositePageTransformer.addTransformer { page, position ->
                                val r = 1 - Math.abs(position)
                                page.scaleY = 0.85f + r * 0.15f
                            }
                            viewPager.setPageTransformer(compositePageTransformer)

                            viewPager.setCurrentItem(1, false)

                            viewPager.registerOnPageChangeCallback(object :
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
                            adapter.startAutoSlide()
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
        viewPager.setCurrentItem(viewPager.currentItem + 1, true)
    }

    private fun setupUI() {

    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as TalkieApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}