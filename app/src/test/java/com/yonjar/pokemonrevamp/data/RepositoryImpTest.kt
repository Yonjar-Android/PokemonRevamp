package com.yonjar.pokemonrevamp.data

import com.yonjar.pokemonrevamp.data.models.PokemonName
import com.yonjar.pokemonrevamp.data.network.PokemonService
import com.yonjar.pokemonrevamp.domain.models.PokemonFullData
import com.yonjar.pokemonrevamp.domain.models.PokemonSimpleModel
import com.yonjar.pokemonrevamp.motherobject.PokemonMotherObject
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull

class RepositoryImpTest{

    @MockK
    lateinit var pokemonService: PokemonService

    private lateinit var repositoryImp: RepositoryImp

    @Before
    fun setUp(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        repositoryImp = RepositoryImp(pokemonService)
    }

    @Test
    fun `getAllPokemon should return a full list of pokemon names`() = runBlocking {
        var response: List<PokemonName>? = null
        //Given
        coEvery { pokemonService.getPokemonList() } returns PokemonMotherObject.pokemonResponse

        //When
        response = repositoryImp.getAllPokemon()

        //Then
        coVerify(exactly = 1) { pokemonService.getPokemonList() }
        assertNotNull(response)
        assertEquals(response, pokemonService.getPokemonList().pokemonList)
    }

    @Test
    fun `getAllPokemon should return null on service failure`() = runBlocking {
        var response: List<PokemonName>? = null
        //Given
        coEvery { pokemonService.getPokemonList() } throws RuntimeException("Service unavailable")

        //When
        response = repositoryImp.getAllPokemon()

        //Then
        assertNull(response)

    }

    @Test
    fun `getRandomPokemon should return a random pokemon name from the full list`() = runBlocking {
        var response:PokemonName? = null

        //Given

        coEvery { pokemonService.getPokemonList() } returns PokemonMotherObject.pokemonResponse

        // When
        response = repositoryImp.getRandomPokemon()

        //Then
        coVerify(exactly = 1) { pokemonService.getPokemonList() }
        assertNotNull(response)

    }

    @Test
    fun `getRandomPokemon should return null on service failure`() = runBlocking {
        var response:PokemonName? = null

        //Given

        coEvery { pokemonService.getPokemonList() } throws RuntimeException("Service unavailable")

        // When
        response = repositoryImp.getRandomPokemon()

        //Then
        assertNull(response)

    }

    @Test
    fun `getPokemonFullData should return a full data for a Pokemon name passed as a parameter`() = runBlocking {
        var response:PokemonFullData? = null

        //Given
        coEvery { pokemonService.getSimplePokemon("bulbasaur") } returns PokemonMotherObject.pokemonFullResponse

        //When

         response = repositoryImp.getPokemonFullData("bulbasaur")

        // Then
        coVerify(exactly = 1) { pokemonService.getSimplePokemon("bulbasaur") }
        assertNotNull(response)
        assertEquals(response?.name,PokemonMotherObject.pokemonFullResponse.name)
        assertEquals(response?.pokedexNumber,PokemonMotherObject.pokemonFullResponse.pokedexNumber)

    }

    @Test
    fun `getPokemonFullData should return null on a service failure`() = runBlocking {
        var response:PokemonFullData? = null

        //Given
        coEvery { pokemonService.getSimplePokemon("bulbasaur") } throws RuntimeException("Service unavailable")

        //When

        response = repositoryImp.getPokemonFullData("bulbasaur")

        // Then
        assertNull(response)

    }

    @Test
    fun `getSimpleInfo should return a PokemonSimpleModel for a Pokemon name passed as a parameter`() = runBlocking {
        var response:PokemonSimpleModel? = null

        // Given
        coEvery { pokemonService.getSimplePokemon("bulbasaur") } returns PokemonMotherObject.pokemonFullResponse

        // When
        response = repositoryImp.getPokemonSimpleInfo("bulbasaur")

        //Then
        assertNotNull(response)
        assertEquals(response?.name,pokemonService.getSimplePokemon("bulbasaur").name)

    }

    @Test
    fun `getSimpleInfo should return a null on a service failure`() = runBlocking {
        var response:PokemonSimpleModel? = null

        // Given
        coEvery { pokemonService.getSimplePokemon("bulbasaur") } throws RuntimeException("Service unavailable")

        // When
        response = repositoryImp.getPokemonSimpleInfo("bulbasaur")

        //Then
        assertNull(response)

    }

}