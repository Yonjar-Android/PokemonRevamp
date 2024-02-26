package com.yonjar.pokemonrevamp.ui.randomPokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.yonjar.pokemonrevamp.databinding.FragmentRandomPokemonBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RandomPokemonFragment : Fragment() {

    private val viewModel: RandomPokemonViewModel by viewModels()

    private var _binding: FragmentRandomPokemonBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRandomPokemonBinding.inflate(inflater, container, false)


        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    when (state) {
                        is RandomPokemonState.Error -> funError(state)
                        RandomPokemonState.Loading -> funLoading()
                        is RandomPokemonState.Success -> funSuccess(state)
                    }
                }
            }
        }
    }

    private fun funError(randomPokemonState: RandomPokemonState.Error) {
        Toast.makeText(requireContext(), "Error: ${randomPokemonState.error}", Toast.LENGTH_SHORT)
            .show()
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun funLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun funSuccess(randomPokemonState: RandomPokemonState.Success) {
        Glide.with(this).load(randomPokemonState.pokemon.spriteShowdown).into(binding.ivPokemon)

        binding.tvPokemon.text = randomPokemonState.pokemon.name.replaceFirst(randomPokemonState.pokemon.name[0], randomPokemonState.pokemon.name[0].uppercaseChar())

        binding.ivPokemon.setOnClickListener {
            navigateToDetail(randomPokemonState.pokemon.name)
        }

        binding.progressBar.visibility = View.GONE

    }

    private fun navigateToDetail(name:String){
        findNavController().navigate(
            RandomPokemonFragmentDirections.actionRandomPokemonFragmentToPokemonDetail(name)
        )
    }

}



