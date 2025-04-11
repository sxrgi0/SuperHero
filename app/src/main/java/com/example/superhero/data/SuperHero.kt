package com.example.superhero.data

import com.google.gson.annotations.SerializedName

data class SuperHeroSearchResponse(val results: List<SuperHero>)

data class SuperHero (val id : String, val name: String, val image: Image, val biography: Biography)

data class Image(val url: String)

data class Biography(@SerializedName ("full-name") val realName: String, @SerializedName("place-of-birth") val placeOfBirth: String, val publisher: String, val alignment: String)