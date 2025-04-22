package com.example.superhero.data

import com.google.gson.annotations.SerializedName

data class SuperHeroSearchResponse(val results: List<SuperHero>)

data class SuperHero (val id : String, val name: String, val image: Image, val biography: Biography, @SerializedName("powerstats") val stats: Stats, val appearance: Appearance)

data class Image(val url: String)

data class Biography(@SerializedName ("full-name") val realName: String, @SerializedName("place-of-birth") val placeOfBirth: String, val publisher: String, val alignment: String)

data class Stats(
    var intelligence: String,
    var strength: String,
    var speed: String,
    var durability: String,
    var power: String,
    var combat: String
)

data class Appearance(val gender: String, val race: String, @SerializedName("eye-color") val eyeColor: String, @SerializedName("hair-color") val hairColor: String)

