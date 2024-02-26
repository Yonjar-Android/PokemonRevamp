package com.yonjar.pokemonrevamp.utils

import com.yonjar.pokemonrevamp.R

object PokemonUtils {

    fun getTypeColor(type:String):Int{
        return when(type){
            "normal" -> R.color.colorNormal
            "fire" -> R.color.colorFire
            "water" -> R.color.colorWater
            "electric" -> R.color.colorElectric
            "grass" ->  R.color.colorGrass
            "ice" -> R.color.colorIce
            "fighting" -> R.color.colorFighting
            "poison" -> R.color.colorPoison
            "ground" ->  R.color.colorGround
            "flying" -> R.color.colorFlying
            "psychic" -> R.color.colorPsychic
            "bug" -> R.color.colorBug
            "rock" -> R.color.colorRock
            "ghost" -> R.color.colorGhost
            "dark" -> R.color.colorDark
            "steel" -> R.color.colorSteel
            "fairy" ->  R.color.colorFairy
            "dragon" -> R.color.colorDragon
            else -> R.color.white
        }
    }



}