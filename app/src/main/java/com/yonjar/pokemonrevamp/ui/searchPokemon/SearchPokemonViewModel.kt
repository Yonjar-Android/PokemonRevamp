package com.yonjar.pokemonrevamp.ui.searchPokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonjar.pokemonrevamp.data.RepositoryImp
import com.yonjar.pokemonrevamp.domain.models.PokemonSimpleModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchPokemonViewModel @Inject constructor(private val repositoryImp: RepositoryImp): ViewModel() {
    private val _state = MutableStateFlow<SearchPokemonState>(SearchPokemonState.Success(
        PokemonSimpleModel("","","","", "")
    ))
    var state:StateFlow<SearchPokemonState> = _state

    fun searchPokemon(pokemonName:String?){
        _state.value = SearchPokemonState.Loading
        viewModelScope.launch {
            try {
            val pokemonResponse = pokemonName?.let { repositoryImp.getPokemonSimpleInfo(it) }

                if(pokemonResponse != null){
                    _state.value = SearchPokemonState.Success(pokemonResponse)
                }else{
                    _state.value = SearchPokemonState.Error("Pokemon not found")
                }
            }
            catch (e:Exception){
                _state.value = SearchPokemonState.Error(e.message ?: "Unknown error")
            }
        }
    }

}