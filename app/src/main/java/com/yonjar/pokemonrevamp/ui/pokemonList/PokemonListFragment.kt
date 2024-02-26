package com.yonjar.pokemonrevamp.ui.pokemonList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yonjar.pokemonrevamp.R
import com.yonjar.pokemonrevamp.databinding.FragmentPokemonListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    private val viewModel: PokemonListViewModel by viewModels()

    private var _binding: FragmentPokemonListBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapterRV: PokemonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }


    private fun initUi() {
        val gridLayout = GridLayoutManager(requireContext(), 2)

        adapterRV =
            PokemonListAdapter(requireContext()) { pokemonName -> navigateToDetail(pokemonName) }

        binding.rvPokemon.apply {
            layoutManager = gridLayout
            adapter = adapterRV
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    when (state) {
                        is PokemonListState.Error -> funError(state)
                        PokemonListState.Loading -> funLoading()
                        is PokemonListState.Success -> funSuccess(state)
                    }
                }
            }
        }
    }

    private fun funSuccess(state: PokemonListState.Success) {

        adapterRV.updateList(state.pokemon)

        binding.idProgressBar.visibility = View.GONE
    }

    private fun funLoading() {
        binding.idProgressBar.visibility = View.VISIBLE
    }

    private fun funError(state: PokemonListState.Error) {
        Toast.makeText(requireContext(), "Error: ${state.error}", Toast.LENGTH_SHORT).show()

        binding.idProgressBar.visibility = View.GONE
    }

    private fun navigateToDetail(pokemonName: String) {
        findNavController().navigate(
            PokemonListFragmentDirections.actionPokemonListFragment2ToPokemonDetail(
                pokemonName
            )
        )
    }

}

