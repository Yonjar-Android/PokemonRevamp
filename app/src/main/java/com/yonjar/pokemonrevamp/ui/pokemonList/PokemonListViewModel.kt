package com.yonjar.pokemonrevamp.ui.pokemonList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonjar.pokemonrevamp.data.RepositoryImp
import com.yonjar.pokemonrevamp.domain.models.PokemonSimpleModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val repositoryImp: RepositoryImp): ViewModel() {

    private val _state = MutableStateFlow<PokemonListState>(PokemonListState.Loading)
    var state:StateFlow<PokemonListState> = _state

    init{
         var counter = 0
         var counter2 = 10

        val pokemonList = mutableListOf<PokemonSimpleModel>()
        viewModelScope.launch(Dispatchers.IO) {
            try{

                val pokemonResponse = repositoryImp.getAllPokemon()


                if(pokemonResponse != null){
                    pokemonResponse.forEach{pokemon ->
                        val pokemon = repositoryImp.getPokemonSimpleInfo(pokemon.name)
                        pokemon?.let { pokemonList.add(pokemon) }
                        counter++
                        if(counter == counter2 || counter == pokemonResponse.size){
                            _state.value = PokemonListState.Success(pokemonList.toList())
                            counter2 += 10
                        }
                    }

                } else{
                    _state.value = PokemonListState.Error("Response was null")
                }
            }
            catch (e:Exception){
                _state.value = PokemonListState.Error(e.message ?: "Unknown error")
            }
        }
    }

}

