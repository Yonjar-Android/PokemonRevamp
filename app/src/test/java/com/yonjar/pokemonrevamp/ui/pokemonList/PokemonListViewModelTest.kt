package com.yonjar.pokemonrevamp.ui.pokemonList

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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListViewModelTest{
    @MockK
    lateinit var repositoryImp: RepositoryImp

    private lateinit var pokemonListViewModel: PokemonListViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.IO)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAllPokemon() should return a full list of the pokemon from the API and after that change the value of the state to Success`() = runBlocking {
        coEvery { repositoryImp.getAllPokemon() } returns PokemonMotherObject.pokemonList
         for (i in PokemonMotherObject.pokemonList.indices){
          coEvery { repositoryImp.getPokemonSimpleInfo(PokemonMotherObject.pokemonList[i].name) } returns PokemonMotherObject.pokemonSimpleModelList[i]
        }

        pokemonListViewModel = PokemonListViewModel(repositoryImp)

        // El delay es necesario para que le de tiempo al viewModel a actualizar su estado,
        // de lo contrario el test dará error ya que todavía no lo habrá actualizado y no habrán coincidencias
        delay(100)

        val state = pokemonListViewModel.state.value

        assertTrue(state is PokemonListState.Success)
        assertEquals((state as PokemonListState.Success).pokemon,PokemonMotherObject.pokemonSimpleModelList)
    }

    @Test
    fun `getAllPokemon() should change the state of the viewModel to Error when an exception occurs`() = runBlocking {
        coEvery { repositoryImp.getAllPokemon() } throws Exception("Unknown error")

        pokemonListViewModel = PokemonListViewModel(repositoryImp)

        delay(100)

        val state = pokemonListViewModel.state.value

        assertTrue(state is PokemonListState.Error)
        assertEquals((state as PokemonListState.Error).error,"Unknown error")
    }

    @Test
    fun `getAllPokemon() should change the value of the state to Error if response is null`() = runBlocking {
        coEvery { repositoryImp.getAllPokemon() } returns null

        pokemonListViewModel = PokemonListViewModel(repositoryImp)

        delay(100)

        val state = pokemonListViewModel.state.value

        assertTrue(state is PokemonListState.Error)
        assertEquals((state as PokemonListState.Error).error,"Response was null")
    }

    @Test
    fun `getPokemonSimpleInfo() should change the state of the viewModel to Error when an exception occurs`() = runBlocking {
        coEvery { repositoryImp.getAllPokemon() } returns PokemonMotherObject.pokemonList
        for (i in PokemonMotherObject.pokemonList.indices){
            coEvery { repositoryImp.getPokemonSimpleInfo(PokemonMotherObject.pokemonList[i].name) } throws Exception("Unknown error")
        }

        pokemonListViewModel = PokemonListViewModel(repositoryImp)

        // El delay es necesario para que le de tiempo al viewModel a actualizar su estado,
        // de lo contrario el test dará error ya que todavía no lo habrá actualizado y no habrán coincidencias
        delay(100)

        val state = pokemonListViewModel.state.value

        assertTrue(state is PokemonListState.Error)
        assertEquals((state as PokemonListState.Error).error,"Unknown error")
    }
}