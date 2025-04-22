package com.example.superhero.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.superhero.R
import com.example.superhero.data.SuperHero
import com.example.superhero.databinding.ActivityDetailBinding
import com.example.superhero.databinding.ActivityMainBinding
import com.example.superhero.utils.SuperHeroService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.graphics.drawable.toDrawable

class DetailActivity : AppCompatActivity() {

    companion object {
        const val SUPERHERO_ID = "SUPERHERO_ID"
    }

    lateinit var binding: ActivityDetailBinding

    lateinit var superhero: SuperHero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = " "

        val id = intent.getStringExtra(SUPERHERO_ID)!!

        getSuperHeroID(id)

        binding.navigationView.setOnItemSelectedListener { menuItem ->
            binding.contentBiography.visibility = View.GONE
            binding.contentAppearance.visibility = View.GONE
            binding.contentStats.visibility = View.GONE

            when (menuItem.itemId) {
                R.id.menu_biography -> binding.contentBiography.visibility = View.VISIBLE
                R.id.menu_appearance -> binding.contentAppearance.visibility = View.VISIBLE
                R.id.menu_stats -> binding.contentStats.visibility = View.VISIBLE
            }
            true
        }

        binding.navigationView.selectedItemId = R.id.menu_biography

    }

    fun getSuperHeroID(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = SuperHeroService.getInstance()
                superhero = service.findSuperHerobyID(id)

                CoroutineScope(Dispatchers.Main).launch {
                    loadData()
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun loadData() {
        supportActionBar?.title = superhero.name
        supportActionBar?.subtitle = superhero.biography.realName
        Picasso.get().load(superhero.image.url).into(binding.avatarImageView)

        binding.alignmentTextView.text = superhero.biography.alignment
        binding.publisherTextView.text = superhero.biography.publisher
        binding.placeofbirthTextView.text = superhero.biography.placeOfBirth

        binding.intelligenceTextView.text = "${superhero.stats.intelligence.toIntOrNull() ?: 0}"
        binding.intelligenceProgress.progress = superhero.stats.intelligence.toIntOrNull() ?: 0

        binding.genderTextView.text = superhero.appearance.gender
        binding.raceTextView.text = superhero.appearance.race
        binding.eyecolorTextView.text = superhero.appearance.eyeColor
        binding.haircolorTextView.text = superhero.appearance.hairColor
    }
}