package com.example.superhero.data

import com.google.gson.TypeAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

data class SuperHeroSearchResponse(val results: List<SuperHero>)

data class SuperHero (
    val id : String,
    val name: String,
    val image: Image,
    val biography: Biography,
    @SerializedName("powerstats") val stats: Stats,
    val appearance: Appearance)

data class Image(val url: String)

data class Biography(
    @SerializedName ("full-name") val realName: String,
    @SerializedName("place-of-birth") val placeOfBirth: String,
    val publisher: String,
    val alignment: String,
    @SerializedName("alter-egos") val alterEgo: String,
    val aliases: List<String>,
    @SerializedName("first-appearance") val firstAppearance: String
    )

data class Stats(
    @JsonAdapter(IntegerAdapter::class) var intelligence: Int,
    @JsonAdapter(IntegerAdapter::class) var strength: Int,
    @JsonAdapter(IntegerAdapter::class) var speed: Int,
    @JsonAdapter(IntegerAdapter::class) var durability: Int,
    @JsonAdapter(IntegerAdapter::class) var power: Int,
    @JsonAdapter(IntegerAdapter::class) var combat: Int
)

data class Appearance(
    val gender: String,
    val race: String,
    @SerializedName("eye-color") val eyeColor: String,
    @SerializedName("hair-color") val hairColor: String,
    val height: List<String>,
    val weight: List<String>)

class IntegerAdapter : TypeAdapter<Int>() {
    override fun write(out: JsonWriter?, value: Int) {
        out?.value(value)
    }

    override fun read(`in`: JsonReader?): Int {
        return try {
            `in`!!.nextString()!!.toInt()
        } catch (e: Exception) {
            0
        }
    }

}

