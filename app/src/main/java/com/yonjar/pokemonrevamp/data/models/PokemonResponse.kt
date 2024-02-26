package com.yonjar.pokemonrevamp.data.models

import com.google.gson.annotations.SerializedName
import com.yonjar.pokemonrevamp.domain.models.PokemonFullData
import com.yonjar.pokemonrevamp.domain.models.PokemonSimpleModel
import java.util.Objects

data class PokemonResponse(
    @SerializedName("name") val name: String,
    @SerializedName("sprites") val sprites: Sprites,
    @SerializedName("weight") val weight: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("order") val pokedexNumber: Int,
    @SerializedName("stats") val stats: List<Stat>,
    @SerializedName("types") val types: List<TypeSlot>
){
    fun toSimpleModel():PokemonSimpleModel = PokemonSimpleModel(
        name = name,
        spriteShowdown = sprites.otherSprites.showdownSprites.frontSprite,
        spriteHome = sprites.otherSprites.homeSprites.homeSprite,
        spriteHomeShiny = sprites.otherSprites.homeSprites.homeShinySprite,
        firstType = types[0].type.name

    )

    fun toFullData(): PokemonFullData = PokemonFullData(name, weight, height, pokedexNumber,
        spritesHome = sprites.otherSprites.homeSprites.homeSprite,
        spriteHomeShiny = sprites.otherSprites.homeSprites.homeShinySprite
        , stats, types)

}

data class Sprites(
    @SerializedName("other") val otherSprites: Other
)

data class Other(
    @SerializedName("showdown") val showdownSprites: Showdown,
    @SerializedName("home") val homeSprites: Home
)

data class Home(
    @SerializedName("front_default") val homeSprite: String,
    @SerializedName("front_shiny") val homeShinySprite: String
)


data class Showdown(
    @SerializedName("front_default") val frontSprite: String
)

data class Stat(
    @SerializedName("base_stat") val baseStat:Int
)

data class TypeSlot(
    val type: Type
)

data class Type(
    val name: String
)
