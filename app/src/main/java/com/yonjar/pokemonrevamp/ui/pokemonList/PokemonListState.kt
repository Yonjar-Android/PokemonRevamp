package com.yonjar.pokemonrevamp.ui.pokemonList

import com.yonjar.pokemonrevamp.domain.models.PokemonSimpleModel

sealed class PokemonListState {
    data object Loading: PokemonListState()
    data class Error(val error:String): PokemonListState()
    data class Success(val pokemon: List<PokemonSimpleModel>): PokemonListState()
}