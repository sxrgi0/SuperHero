package com.example.superhero.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.superhero.R
import com.example.superhero.adapters.SuperheroAdapter
import com.example.superhero.data.SuperHero
import com.example.superhero.data.SuperHeroSearchResponse
import com.example.superhero.databinding.ActivityMainBinding
import com.example.superhero.utils.SuperHeroService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: SuperheroAdapter

    var superHeroList: List<SuperHero> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = SuperheroAdapter(superHeroList) { position: Int ->
            val superhero = superHeroList[position]

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.SUPERHERO_ID,superhero.id)
            startActivity(intent)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        searchSuperHero("a")


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)

        val menuItem = menu.findItem(R.id.menu_search)
        val searchView = menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchSuperHero(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })

        return true
    }

    fun searchSuperHero(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = SuperHeroService.getInstance()
                val response = service.findSuperHerobyName(query)
                superHeroList = response.results
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateItems(superHeroList)
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}