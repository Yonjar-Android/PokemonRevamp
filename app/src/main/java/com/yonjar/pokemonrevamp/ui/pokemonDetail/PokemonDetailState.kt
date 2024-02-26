package com.yonjar.pokemonrevamp.ui.pokemonDetail

import com.yonjar.pokemonrevamp.domain.models.PokemonFullData

sealed class PokemonDetailState {
    data object Loading: PokemonDetailState()
    data class Error(val error:String): PokemonDetailState()
    data class Success(val pokemonData:PokemonFullData): PokemonDetailState()
}