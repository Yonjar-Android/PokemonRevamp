package com.yonjar.pokemonrevamp.ui.pokemonDetail

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
class PokemonDetailViewModelTest{

    @MockK
    lateinit var repositoryImp: RepositoryImp

    private lateinit var pokemonDetailViewModel: PokemonDetailViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.IO)
        pokemonDetailViewModel = PokemonDetailViewModel(repositoryImp)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getDetail() should change the value of the state to Success`() = runBlocking {
        coEvery { repositoryImp.getPokemonFullData("bulbasaur") } returns PokemonMotherObject.pokemonFull

        pokemonDetailViewModel.getDetail("bulbasaur")

        delay(100)

        val state = pokemonDetailViewModel.state.value

        assertTrue(state is PokemonDetailState.Success)
        assertEquals((state as PokemonDetailState.Success).pokemonData,PokemonMotherObject.pokemonFull)
    }

    @Test
    fun `getDetail() should change the value of the state to Error if response is null`() = runBlocking {
        coEvery { repositoryImp.getPokemonFullData("bulbasau") } returns null

        pokemonDetailViewModel.getDetail("bulbasau")

        delay(100)

        val state = pokemonDetailViewModel.state.value

        assertTrue(state is PokemonDetailState.Error)
        assertEquals((state as PokemonDetailState.Error).error,"Response was null")
    }

    @Test
    fun `getDetail() should change the state of the viewModel to Error when an exception occurs`() = runBlocking {
        coEvery { repositoryImp.getPokemonFullData("bulbasaur") } throws Exception("Unknown error")


        pokemonDetailViewModel.getDetail("bulbasaur")

        // El delay es necesario para que le de tiempo al viewModel a actualizar su estado,
        // de lo contrario el test dará error ya que todavía no lo habrá actualizado y no habrán coincidencias
        delay(100)

        val state = pokemonDetailViewModel.state.value

        assertTrue(state is PokemonDetailState.Error)
        assertEquals((state as PokemonDetailState.Error).error,"Unknown error")
    }

}