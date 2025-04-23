package com.example.superhero.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val id = intent.getStringExtra(SUPERHERO_ID)!!

        getSuperHeroID(id)

        binding.navigationView.setOnItemSelectedListener { menuItem ->
            binding.contentBiography.root.visibility = View.GONE
            binding.contentAppearance.root.visibility = View.GONE
            binding.contentStats.root.visibility = View.GONE

            when (menuItem.itemId) {
                R.id.menu_biography -> binding.contentBiography.root.visibility = View.VISIBLE
                R.id.menu_appearance -> binding.contentAppearance.root.visibility = View.VISIBLE
                R.id.menu_stats -> binding.contentStats.root.visibility = View.VISIBLE
            }
            true
        }

        binding.navigationView.selectedItemId = R.id.menu_biography

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
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

        //Biography
        binding.contentBiography.alignmentTextView.text = superhero.biography.alignment
        binding.contentBiography.publisherTextView.text = superhero.biography.publisher
        binding.contentBiography.placeofbirthTextView.text = superhero.biography.placeOfBirth
        binding.contentBiography.firstAppearanceTextView.text = superhero.biography.firstAppearance
        binding.contentBiography.alterEgoTextView.text = superhero.biography.alterEgo

        //Stats
        createChart()

        //Appearance
        binding.contentAppearance.genderTextView.text = superhero.appearance.gender
        binding.contentAppearance.raceTextView.text = superhero.appearance.race
        binding.contentAppearance.eyecolorTextView.text = superhero.appearance.eyeColor
        binding.contentAppearance.haircolorTextView.text = superhero.appearance.hairColor
        binding.contentAppearance.weightTextView.text = superhero.appearance.weight[1]
        binding.contentAppearance.heightTextView.text = superhero.appearance.height[1]
    }

    private fun createChart(){
        var entries = listOf(
            RadarEntry(superhero.stats.intelligence.toFloat()),
            RadarEntry(superhero.stats.strength.toFloat()),
            RadarEntry(superhero.stats.speed.toFloat()),
            RadarEntry(superhero.stats.durability.toFloat()),
            RadarEntry(superhero.stats.power.toFloat()),
            RadarEntry(superhero.stats.combat.toFloat())
        )

        val labels = listOf("Intelligence", "Strength", "Speed", "Durability", "Power", "Combat")

        val dataset = RadarDataSet(entries, "Stats").apply {
            color = ContextCompat.getColor(this@DetailActivity, R.color.lineColor)
            fillColor = ContextCompat.getColor(this@DetailActivity, R.color.fillColor)
            setDrawFilled(true)
            lineWidth = 2f
        }

        val data = RadarData(dataset)
        binding.contentStats.radarChart.data = data

        binding.contentStats.radarChart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(labels)
            textSize = 10f
        }
        binding.contentStats.radarChart.yAxis.apply {
            axisMinimum = 0f
            axisMaximum = 100f
            textSize = 8f
        }
        binding.contentStats.radarChart.description.isEnabled = false
        binding.contentStats.radarChart.isRotationEnabled = false
        binding.contentStats.radarChart.invalidate()

    }
}