package com.yonjar.pokemonrevamp.ui.searchPokemon

import com.yonjar.pokemonrevamp.domain.models.PokemonSimpleModel

sealed class SearchPokemonState {
    data object Loading: SearchPokemonState()
    data class Error(val error:String): SearchPokemonState()
    data class Success(val pokemon: PokemonSimpleModel): SearchPokemonState()
}