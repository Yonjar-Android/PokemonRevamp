package com.yonjar.pokemonrevamp.ui.pokemonList.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.yonjar.pokemonrevamp.domain.models.PokemonSimpleModel

class PokemonListDiffUtil(
    private val oldList:List<PokemonSimpleModel>,
    private val newList:List<PokemonSimpleModel>
    ):DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}