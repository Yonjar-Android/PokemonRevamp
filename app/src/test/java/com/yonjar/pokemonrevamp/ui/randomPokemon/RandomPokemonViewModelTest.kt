package com.yonjar.pokemonrevamp.ui.randomPokemon

import com.yonjar.pokemonrevamp.data.RepositoryImp
import com.yonjar.pokemonrevamp.data.models.PokemonName
import com.yonjar.pokemonrevamp.motherobject.PokemonMotherObject
import com.yonjar.pokemonrevamp.ui.pokemonDetail.PokemonDetailViewModel
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
import org.junit.jupiter.api.Assertions.*


@OptIn(ExperimentalCoroutinesApi::class)
class RandomPokemonViewModelTest{

    @MockK
    lateinit var repositoryImp: RepositoryImp

    private lateinit var randomPokemonViewModel: RandomPokemonViewModel

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
    fun `getRandomPokemon() should return a PokemonName and later change the value of the state to Success`() = runBlocking {
        coEvery { repositoryImp.getRandomPokemon() } returns PokemonMotherObject.pokemonName
        coEvery { repositoryImp.getPokemonSimpleInfo("bulbasaur") } returns PokemonMotherObject.pokemonSimpleModel

        randomPokemonViewModel = RandomPokemonViewModel(repositoryImp)

        // El delay es necesario para que le de tiempo al viewModel a actualizar su estado,
        // de lo contrario el test dará error ya que todavía no lo habrá actualizado y no habrán coincidencias
        delay(100)

        val state = randomPokemonViewModel.state.value

        assertTrue(state is RandomPokemonState.Success)
        assertEquals((state as RandomPokemonState.Success).pokemon.name,PokemonMotherObject.pokemonName.name)
    }

    @Test
    fun `getRandomPokemon() should change the value of the state to Error if response is null`() = runBlocking {
        coEvery { repositoryImp.getRandomPokemon() } returns null

        randomPokemonViewModel = RandomPokemonViewModel(repositoryImp)

        delay(100)

        val state = randomPokemonViewModel.state.value

        assertTrue(state is RandomPokemonState.Error)
        assertEquals((state as RandomPokemonState.Error).error,"Response was null")
    }

    @Test
    fun `getRandomPokemon() should change the state of the viewModel to Error when an exception occurs`() = runBlocking {
        coEvery { repositoryImp.getRandomPokemon() } throws Exception("Unknown error")

        randomPokemonViewModel = RandomPokemonViewModel(repositoryImp)

        delay(100)

        val state = randomPokemonViewModel.state.value

        assertTrue(state is RandomPokemonState.Error)
        assertEquals((state as RandomPokemonState.Error).error,"Unknown error")
    }

    @Test
    fun `getPokemonSimpleInfo() should change the state of the viewModel to Error when an exception occurs`() = runBlocking {
        coEvery { repositoryImp.getRandomPokemon() } returns PokemonMotherObject.pokemonName
        coEvery { repositoryImp.getPokemonSimpleInfo("bulbasaur") } throws Exception("Unknown error")

        randomPokemonViewModel = RandomPokemonViewModel(repositoryImp)

        delay(100)

        val state = randomPokemonViewModel.state.value

        assertTrue(state is RandomPokemonState.Error)
        assertEquals((state as RandomPokemonState.Error).error,"Unknown error")
    }
}