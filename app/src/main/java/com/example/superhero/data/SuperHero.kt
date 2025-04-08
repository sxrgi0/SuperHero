package com.example.superhero.data

data class SuperHeroSearchResponse(val results: List<SuperHero>)

data class SuperHero (val id : String, val name: String, val image: Image)

data class Image(val url: String)