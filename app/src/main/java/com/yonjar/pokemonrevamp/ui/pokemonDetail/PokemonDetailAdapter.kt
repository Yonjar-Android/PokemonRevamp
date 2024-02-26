package com.yonjar.pokemonrevamp.ui.pokemonDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yonjar.pokemonrevamp.R
import com.yonjar.pokemonrevamp.data.models.TypeSlot

class PokemonDetailAdapter(
    private var listType: List<TypeSlot> = mutableListOf(),
    private val context: Context
) : RecyclerView.Adapter<PokemonDetailViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonDetailViewHolder {
        return PokemonDetailViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.type_card, parent, false)
        )
    }

    override fun getItemCount(): Int = listType.size

    override fun onBindViewHolder(holder: PokemonDetailViewHolder, position: Int) {
        holder.render(listType[position],context)
    }

    fun updateList(newList: List<TypeSlot>) {
        listType = newList
        notifyDataSetChanged()
    }

}