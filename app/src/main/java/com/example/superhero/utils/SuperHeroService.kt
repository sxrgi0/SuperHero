package com.example.superhero.utils

import com.example.superhero.data.SuperHero
import com.example.superhero.data.SuperHeroSearchResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface SuperHeroService {

    @GET("search/{name}")
    suspend fun findSuperHerobyName(@Path("name") name: String): SuperHeroSearchResponse

    @GET("{character-id}")
    suspend fun findSuperHerobyID(@Path("character-id") id: String): SuperHero

    companion object{
        fun getInstance(): SuperHeroService {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://superheroapi.com/api/61792526c8ce513fd45a01c8b2d89e3f/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

            return retrofit.create(SuperHeroService::class.java)

        }
    }
}