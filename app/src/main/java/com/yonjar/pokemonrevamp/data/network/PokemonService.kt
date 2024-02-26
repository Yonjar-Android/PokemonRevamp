package com.yonjar.pokemonrevamp.data.network

import com.yonjar.pokemonrevamp.data.models.PokemonResponse
import com.yonjar.pokemonrevamp.data.models.ResultsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon/{pokemonName}")
    suspend fun getSimplePokemon(@Path("pokemonName") pokemonName:String): PokemonResponse

    @GET("pokemon/?offset=0&limit=600")
    suspend fun getPokemonList(): ResultsResponse

}