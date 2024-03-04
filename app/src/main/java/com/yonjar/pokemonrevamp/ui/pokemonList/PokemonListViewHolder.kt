package com.yonjar.pokemonrevamp.ui.pokemonList

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yonjar.pokemonrevamp.databinding.PokemonCardBinding
import com.yonjar.pokemonrevamp.domain.models.PokemonSimpleModel
import com.yonjar.pokemonrevamp.utils.PokemonUtils

class PokemonListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = PokemonCardBinding.bind(itemView)

    fun render(pokemon:PokemonSimpleModel, context: Context, goDetail:(String) -> Unit){
        Glide.with(context).load(pokemon.spriteHome).into(binding.ivPokemon)
        binding.tvPokemonName.text = pokemon.name.replaceFirst(pokemon.name[0],pokemon.name[0].uppercaseChar())

        val colorType = PokemonUtils.getTypeColor(pokemon.firstType)

        binding.layoutCard.setBackgroundColor(ContextCompat.getColor(context,colorType))

        itemView.setOnClickListener {
            goDetail(pokemon.name)
        }
    }

}