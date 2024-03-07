package com.yonjar.pokemonrevamp.ui.searchPokemon

import com.yonjar.pokemonrevamp.data.RepositoryImp
import com.yonjar.pokemonrevamp.motherobject.PokemonMotherObject
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SearchPokemonViewModelTest{

    @MockK
    lateinit var repositoryImp: RepositoryImp

    lateinit var searchPokemonViewModel: SearchPokemonViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.IO)
        searchPokemonViewModel = SearchPokemonViewModel(repositoryImp)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }
    @Test
    fun `searchPokemon should change the state of the viewModel to Success`() = runBlocking {

        coEvery { repositoryImp.getPokemonSimpleInfo("bulbasaur") } returns PokemonMotherObject.pokemonSimpleModel

        // El delay es necesario para que le de tiempo al viewModel a actualizar su estado,
        // de lo contrario el test dará error ya que todavía no lo habrá actualizado y no habrán coincidencias
        searchPokemonViewModel.searchPokemon("bulbasaur")


        delay(100)


        val state = searchPokemonViewModel.state.value


        assertTrue(state is SearchPokemonState.Success)
    }

    @Test
    fun `searchPokemon should change the state of the viewModel to Error`() = runBlocking {

        coEvery { repositoryImp.getPokemonSimpleInfo("bulbasaur") } returns null


        searchPokemonViewModel.searchPokemon("bulbasaur")


        delay(100)


        val state = searchPokemonViewModel.state.value


        assertTrue(state is SearchPokemonState.Error)
    }

    @Test
    fun `searchPokemon should change the state of the viewModel to Error when an exception occurs`() = runBlocking {
        // Configurar el repositorio para lanzar una excepción
        coEvery { repositoryImp.getPokemonSimpleInfo("bulbasaur") } throws Exception("Unknown error")

        // Llamar a la función de búsqueda
        searchPokemonViewModel.searchPokemon("bulbasaur")

        // Esperar a que la operación asíncrona se complete
        delay(100)

        // Obtener el estado actual del ViewModel
        val state = searchPokemonViewModel.state.value

        // Verificar que el estado es Error y contiene el mensaje de la excepción simulada
        assertTrue(state is SearchPokemonState.Error)
        assertTrue((state as SearchPokemonState.Error).error == "Unknown error")
    }

}