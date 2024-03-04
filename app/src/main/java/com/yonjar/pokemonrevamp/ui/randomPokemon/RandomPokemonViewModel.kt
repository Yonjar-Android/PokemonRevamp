package com.yonjar.pokemonrevamp.ui.randomPokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonjar.pokemonrevamp.data.RepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomPokemonViewModel @Inject constructor(private val repositoryImp: RepositoryImp): ViewModel() {

    private val _state = MutableStateFlow<RandomPokemonState>( RandomPokemonState.Loading)
    var state:StateFlow<RandomPokemonState> = _state

    init {
        viewModelScope.launch {
             try {
                 val response = repositoryImp.getRandomPokemon()

                 if(response != null){

                     _state.value = RandomPokemonState.Success(repositoryImp.getPokemonSimpleInfo(response.name)!!)
                 }
                 else{
                     _state.value = RandomPokemonState.Error("Response was null")
                 }
             }

             catch (e:Exception){
                 _state.value = RandomPokemonState.Error(e.message ?: "Unknown error")
             }
        }
    }
}
