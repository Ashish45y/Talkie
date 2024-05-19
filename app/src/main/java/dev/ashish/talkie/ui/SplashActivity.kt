package dev.ashish.talkie.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.ashish.talkie.R
import dev.ashish.talkie.databinding.ActivitySplashBinding
import dev.ashish.talkie.utils.ANIMATION_DURATION
import dev.ashish.talkie.utils.INITIAL_DELAY
import dev.ashish.talkie.utils.LOTTIE_TRANSLATION_Y
import dev.ashish.talkie.utils.SPLASH_TRANSLATION_Y

class SplashActivity : AppCompatActivity() {
   private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
       binding.splashImg.postDelayed({
            binding.splashImg.animate().translationY(SPLASH_TRANSLATION_Y).setDuration(ANIMATION_DURATION).withEndAction {
                binding.lottieLayerName.animate().translationY(LOTTIE_TRANSLATION_Y).setDuration(ANIMATION_DURATION).withEndAction {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()  // Optional: Finish the current activity if you don't want to come back to it
                }
            }
        }, INITIAL_DELAY)
    }
}