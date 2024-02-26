package com.yonjar.pokemonrevamp.domain

import com.yonjar.pokemonrevamp.data.models.PokemonName
import com.yonjar.pokemonrevamp.domain.models.PokemonFullData
import com.yonjar.pokemonrevamp.domain.models.PokemonSimpleModel

interface Repository {

    suspend fun getAllPokemon():List<PokemonName>?

    suspend fun getRandomPokemon():PokemonName?

    suspend fun getPokemonSimpleInfo(name:String):PokemonSimpleModel?

    suspend fun getPokemonFullData(name:String): PokemonFullData?

}