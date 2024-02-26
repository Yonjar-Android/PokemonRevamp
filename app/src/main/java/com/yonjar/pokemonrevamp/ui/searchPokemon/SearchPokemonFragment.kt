package com.yonjar.pokemonrevamp.ui.searchPokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.yonjar.pokemonrevamp.databinding.FragmentSearchPokemonBinding
import com.yonjar.pokemonrevamp.ui.pokemonList.PokemonListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchPokemonFragment : Fragment() {

    val viewModel:SearchPokemonViewModel by viewModels()

    private var _binding: FragmentSearchPokemonBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchPokemonBinding.inflate(inflater, container, false)

        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initUI()

    }

    private fun initListeners() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchPokemon(query?.lowercase())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })


    }

    private fun initUI(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collectLatest { state ->
                    when(state){
                        is SearchPokemonState.Error -> funError(state)
                        SearchPokemonState.Loading ->  funLoading()
                        is SearchPokemonState.Success -> funSuccess(state)
                    }
                }
            }
        }
    }

    private fun funError(state: SearchPokemonState.Error) {
        Toast.makeText(requireContext(),"Error: ${state.error}",Toast.LENGTH_SHORT).show()
        binding.idProgressBar.visibility = View.GONE
    }

    private fun funLoading(){
        binding.idProgressBar.visibility = View.VISIBLE
    }

    private fun funSuccess(state: SearchPokemonState.Success) {
        if(state.pokemon.spriteHome.isNotEmpty()){
            Glide.with(requireContext()).load(state.pokemon.spriteHome).into(binding.ivPokemon)

            binding.ivPokemon.setOnClickListener {
                navigateToDetail(state.pokemon.name)
            }

            binding.idProgressBar.visibility = View.GONE
        }
    }

    private fun navigateToDetail(name:String){
        findNavController().
        navigate(SearchPokemonFragmentDirections.actionSearchPokemonFragmentToPokemonDetail(name))
    }

}