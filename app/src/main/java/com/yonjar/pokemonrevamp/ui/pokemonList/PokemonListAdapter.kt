package com.yonjar.pokemonrevamp.ui.pokemonList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.yonjar.pokemonrevamp.R
import com.yonjar.pokemonrevamp.domain.models.PokemonSimpleModel


class PokemonListAdapter(

    private val context: Context,
    private var pokemons: List<PokemonSimpleModel> = listOf(),
    private val goDetail: (String) -> Unit

) : RecyclerView.Adapter<PokemonListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_card, parent, false)

        return PokemonListViewHolder(view)

    }

    override fun getItemCount(): Int = pokemons.size

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        holder.render(pokemons[position], context, goDetail)
    }

    fun updateList(list: List<PokemonSimpleModel>) {
        pokemons = list
        notifyDataSetChanged()
    }

}

