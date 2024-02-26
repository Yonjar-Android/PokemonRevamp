package com.yonjar.pokemonrevamp.domain.models

import com.yonjar.pokemonrevamp.data.models.Stat
import com.yonjar.pokemonrevamp.data.models.TypeSlot

data class PokemonFullData(
    val name: String,
    val weight: Int,
    val height: Int,
    val pokedexNumber: Int,
    val spritesHome: String,
    val spriteHomeShiny:String,
    val stats: List<Stat>,
    val types: List<TypeSlot>
)