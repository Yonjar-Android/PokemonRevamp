package com.yonjar.pokemonrevamp.data

import com.yonjar.pokemonrevamp.data.models.PokemonName
import com.yonjar.pokemonrevamp.data.network.PokemonService
import com.yonjar.pokemonrevamp.domain.Repository
import com.yonjar.pokemonrevamp.domain.models.PokemonFullData
import com.yonjar.pokemonrevamp.domain.models.PokemonSimpleModel
import javax.inject.Inject


class RepositoryImp @Inject constructor(private val service: PokemonService) : Repository {
    override suspend fun getAllPokemon(): List<PokemonName>? {
        runCatching {
            service.getPokemonList()
        }.onFailure { println("${it.message}") }
            .onSuccess { return it.pokemonList }
        return null
    }

    override suspend fun getRandomPokemon(): PokemonName? {
        runCatching {
            service.getPokemonList()
        }.onFailure { println("${it.message}") }
            .onSuccess {  return it.pokemonList.random() }
        return null
    }

    override suspend fun getPokemonFullData(name: String): PokemonFullData? {

        runCatching {
            service.getSimplePokemon(name)
        }.onFailure { println("${it.message}") }
            .onSuccess { return it.toFullData()}

        return null
    }

    override suspend fun getPokemonSimpleInfo(name: String): PokemonSimpleModel? {
        runCatching {
            service.getSimplePokemon(name)
        }.onFailure { println("${it.message}") }
            .onSuccess { return  it.toSimpleModel() }
        return null
    }
}