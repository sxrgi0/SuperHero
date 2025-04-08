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
import com.squareup.picasso.Picasso

class SuperheroAdapter(var items: List<SuperHero>) : Adapter<SuperHeroViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false)
        return SuperHeroViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
        val superhero = items[position]
        holder.render(superhero)
    }

    fun updateItems(items: List<SuperHero>) {
        this.items = items
        notifyDataSetChanged()
    }

}

class SuperHeroViewHolder(view : View) : ViewHolder(view){
    var nameTextView: TextView = view.findViewById(R.id.nameTextView)
    var avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)

    fun render(superHero: SuperHero){
        nameTextView.text = superHero.name
        Picasso.get().load(superHero.image.url).into(avatarImageView);

    }

}