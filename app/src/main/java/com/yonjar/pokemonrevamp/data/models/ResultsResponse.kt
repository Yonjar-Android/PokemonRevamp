package com.yonjar.pokemonrevamp.data.models

import com.google.gson.annotations.SerializedName

data class ResultsResponse(
    @SerializedName("count") val count:Int,
    @SerializedName("results") val pokemonList:List<PokemonName>
)

data class PokemonName(
    @SerializedName("name")val name:String
)