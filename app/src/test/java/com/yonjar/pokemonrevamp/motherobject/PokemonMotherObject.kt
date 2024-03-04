package com.yonjar.pokemonrevamp.motherobject

import com.yonjar.pokemonrevamp.data.models.Home
import com.yonjar.pokemonrevamp.data.models.Other
import com.yonjar.pokemonrevamp.data.models.PokemonName
import com.yonjar.pokemonrevamp.data.models.PokemonResponse
import com.yonjar.pokemonrevamp.data.models.ResultsResponse
import com.yonjar.pokemonrevamp.data.models.Showdown
import com.yonjar.pokemonrevamp.data.models.Sprites
import com.yonjar.pokemonrevamp.data.models.Stat
import com.yonjar.pokemonrevamp.data.models.Type
import com.yonjar.pokemonrevamp.data.models.TypeSlot
import com.yonjar.pokemonrevamp.domain.models.PokemonFullData
import com.yonjar.pokemonrevamp.domain.models.PokemonSimpleModel

object PokemonMotherObject {

    private val pokemonList = listOf(
        PokemonName("bulbasaur"),
        PokemonName("ivysaur"),
        PokemonName("venusaur")
    )

    val pokemonResponse = ResultsResponse(
        1302,
        pokemonList
    )

    val pokemonFull = PokemonFullData(
        "bulbasaur", 69, 7, 1,
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/1.png",
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/shiny/1.png",
        listOf(Stat(45), Stat(49), Stat(49), Stat(65), Stat(65), Stat(45)),
        listOf(TypeSlot(Type("grass")), TypeSlot(Type("poison")))
    )

    val pokemonFullResponse = PokemonResponse(
        name = "bulbasaur", weight = 69, height = 7, pokedexNumber = 1,
        sprites = Sprites(
            Other(
                Showdown(
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/1.gif"
                ),
                Home(
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/1.png",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/shiny/1.png"
                )
            )
        ),
        stats = listOf(Stat(45), Stat(49), Stat(49), Stat(65), Stat(65), Stat(45)),
        types = listOf(TypeSlot(Type("grass")), TypeSlot(Type("poison")))
    )

    val pokemonSimpleModel = PokemonSimpleModel(
        name = "bulbasaur",
        spriteHomeShiny = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/shiny/1.png",
        spriteHome = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/1.png",
        spriteShowdown = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/1.gif",
        firstType = "grass"
    )

    val pokemonName = PokemonName("bulbasaur")
}