package com.yonjar.pokemonrevamp.ui.pokemonDetail

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.yonjar.pokemonrevamp.data.models.Type
import com.yonjar.pokemonrevamp.data.models.TypeSlot
import com.yonjar.pokemonrevamp.databinding.TypeCardBinding
import com.yonjar.pokemonrevamp.utils.PokemonUtils

class PokemonDetailViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
    private val binding = TypeCardBinding.bind(itemView)

    fun render(type: TypeSlot, context: Context){
        binding.tvType.text = type.type.name

        val colorType = PokemonUtils.getTypeColor(type.type.name)

        binding.layoutCard.setBackgroundColor(ContextCompat.getColor(context,colorType))
    }
}