package com.yonjar.pokemonrevamp.ui.pokemonDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonjar.pokemonrevamp.data.RepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val repositoryImp: RepositoryImp):ViewModel() {

    private val _state = MutableStateFlow<PokemonDetailState>(PokemonDetailState.Loading)
    var state:StateFlow<PokemonDetailState> = _state

    fun getDetail(name:String){
        _state.value = PokemonDetailState.Loading
        viewModelScope.launch {
            try {
            val pokemonResponse = repositoryImp.getPokemonFullData(name)
                if (pokemonResponse != null){
                    _state.value = PokemonDetailState.Success(pokemonResponse)
                } else{
                    _state.value = PokemonDetailState.Error("Response was null")
                }
            }
            catch (e:Exception){
                _state.value = PokemonDetailState.Error(e.message ?: "Unknown error")
            }
        }
    }

}