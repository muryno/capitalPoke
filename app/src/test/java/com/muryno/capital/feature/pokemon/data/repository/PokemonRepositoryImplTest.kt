package com.muryno.capital.feature.pokemon.data.repository

import com.muryno.capital.core.utils.Result
import com.muryno.capital.feature.pokemon.data.model.PokemonDetailResponse
import com.muryno.capital.feature.pokemon.data.model.PokemonListResponse
import com.muryno.capital.feature.pokemon.data.model.PokemonNetworkItem
import com.muryno.capital.feature.pokemon.data.model.SpritesResponse
import com.muryno.capital.feature.pokemon.data.remote.PokemonApi
import com.muryno.capital.feature.pokemon.domain.model.PaginationParams
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class PokemonRepositoryImplTest {
    private lateinit var repository: PokemonRepositoryImpl
    private lateinit var api: PokemonApi

    @Before
    fun setup() {
        api = mockk()
        repository = PokemonRepositoryImpl(api)
    }

    @Test
    fun `getPokemonList returns success with mapped pokemon list when API call succeeds`() = runTest {
        // Given
        val pokemonList = listOf(
            PokemonNetworkItem(name = "bulbasaur", url = "https://pokeapi.co/api/v2/pokemon/1/")
        )
        val response = PokemonListResponse(count = 1, next = null, previous = null, results = pokemonList)
        coEvery { api.getPokemonList(any(), any()) } returns response

        // When
        val result = repository.getPokemonList(PaginationParams(0))

        // Then
        assertTrue(result is Result.Success)
        assertEquals(1, (result as Result.Success).data.size)
        assertEquals("bulbasaur", result.data[0].name)
    }

    @Test
    fun `getPokemonList returns error when API call fails with exception`() = runTest {
        // Given
        coEvery { api.getPokemonList(any(), any()) } throws IOException("Network error")

        // When
        val result = repository.getPokemonList(PaginationParams(0))

        // Then
        require(result is Result.Error)
        assertEquals("Network error", result.exception.message)
    }

    @Test
    fun `getPokemonDetail returns success with mapped pokemon when API call succeeds`() = runTest {
        // Given
        val pokemonId = 1
        val response = PokemonDetailResponse(
            id = pokemonId,
            name = "bulbasaur",
            height = 7,
            sprites = SpritesResponse("https://example.com/bulbasaur.png")
        )
        coEvery { api.getPokemonDetail(pokemonId) } returns response

        // When
        val result = repository.getPokemonDetail(pokemonId)

        // Then
        require(result is Result.Success)
        assertEquals(pokemonId, result.data.id)
        assertEquals("bulbasaur", result.data.name)
        assertEquals(7, result.data.height)
        assertEquals("https://example.com/bulbasaur.png", result.data.imageUrl)
    }

    @Test
    fun `getPokemonDetail returns error when API call fails with exception`() = runTest {
        // Given
        val pokemonId = 1
        coEvery { api.getPokemonDetail(pokemonId) } throws IOException("Network error")

        // When
        val result = repository.getPokemonDetail(pokemonId)

        // Then
        require(result is Result.Error)
        assertEquals("Network error", result.exception.message)
    }
} 