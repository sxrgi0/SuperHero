package com.example.superhero.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.superhero.R
import com.example.superhero.data.SuperHero
import com.example.superhero.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso

class SuperheroAdapter(var items: List<SuperHero>, val onItemClick: (position: Int)-> Unit) : Adapter<SuperHeroViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false)
        val binding = ItemSuperheroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SuperHeroViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
        val superhero = items[position]
        holder.render(superhero)
        holder.itemView.setOnClickListener {
            onItemClick (position)
        }
    }

    fun updateItems(items: List<SuperHero>) {
        this.items = items
        notifyDataSetChanged()
    }

}

class SuperHeroViewHolder(val binding : ItemSuperheroBinding) : ViewHolder(binding.root){

    fun render(superHero: SuperHero){
        binding.nameTextView.text = superHero.name
        Picasso.get().load(superHero.image.url).into(binding.avatarImageView);

    }

}