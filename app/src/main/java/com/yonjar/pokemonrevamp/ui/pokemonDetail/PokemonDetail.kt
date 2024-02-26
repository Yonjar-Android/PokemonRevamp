package com.yonjar.pokemonrevamp.ui.pokemonDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.yonjar.pokemonrevamp.R
import com.yonjar.pokemonrevamp.databinding.ActivityPokemonDetailBinding
import com.yonjar.pokemonrevamp.domain.models.PokemonFullData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class PokemonDetail : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding

    private val viewModel: PokemonDetailViewModel by viewModels()

    private lateinit var adapterRV:PokemonDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val args: PokemonDetailArgs by navArgs()

        viewModel.getDetail(args.name)
        initUI()
    }

    private fun initUI() {
        adapterRV = PokemonDetailAdapter(context = this)
        val layout = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        binding.rvTypes.apply {
            adapter = adapterRV
            layoutManager = layout
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    when (state) {
                        is PokemonDetailState.Error -> funError(state)
                        PokemonDetailState.Loading -> funLoading()
                        is PokemonDetailState.Success -> funSuccess(state)
                    }
                }
            }
        }
    }

    private fun funLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun funSuccess(state: PokemonDetailState.Success) {

        Glide.with(this).load(state.pokemonData.spriteHomeShiny)
            .into(binding.ivPokemon)

        adapterRV.updateList(state.pokemonData.types)

        showStats(
            binding.viewHP,
            state.pokemonData.stats[0].baseStat,
            binding.tvHp
        )
        showStats(
            binding.viewAttack,
            state.pokemonData.stats[1].baseStat,
            binding.tvAttack
        )
        showStats(
            binding.viewDefense,
            state.pokemonData.stats[2].baseStat,
            binding.tvDefense
        )
        showStats(
            binding.viewSpeAttack,
            state.pokemonData.stats[3].baseStat,
            binding.tvSpeAttack
        )
        showStats(
            binding.viewSpeDefense,
            state.pokemonData.stats[4].baseStat,
            binding.tvSpeDefense
        )
        showStats(
            binding.viewSpeed,
            state.pokemonData.stats[5].baseStat,
            binding.tvSpeed
        )

        binding.tvPokemonName.text = state.pokemonData.name.replaceFirst(state.pokemonData.name[0], state.pokemonData.name[0].uppercaseChar())
        binding.tvHeight.text =
            getString(R.string.height, state.pokemonData.height.toFloat().times(10))
        binding.tvWeight.text =
            getString(R.string.weight, state.pokemonData.weight.toFloat().div(10))

        binding.progressBar.visibility = View.GONE
    }

    private fun funError(state: PokemonDetailState.Error) {
        Toast.makeText(this, "Error: ${state.error}", Toast.LENGTH_SHORT).show()
        binding.progressBar.visibility = View.GONE
    }

    private fun showStats(view: View, pokemonStat: Int, textView: TextView) {
        val params = view.layoutParams
        params.height = pxToDp(pokemonStat.toFloat())
        view.layoutParams = params

        textView.text =  pokemonStat.toString()
    }

    private fun pxToDp(px: Float): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics)
            .roundToInt()
}

