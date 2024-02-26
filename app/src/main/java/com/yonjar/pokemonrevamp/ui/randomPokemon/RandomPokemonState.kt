package com.yonjar.pokemonrevamp.ui.randomPokemon

import com.yonjar.pokemonrevamp.domain.models.PokemonSimpleModel

sealed class RandomPokemonState {

    data object Loading : RandomPokemonState()

    data class Error(val error: String) : RandomPokemonState()

    data class Success(val pokemon: PokemonSimpleModel) : RandomPokemonState()

}