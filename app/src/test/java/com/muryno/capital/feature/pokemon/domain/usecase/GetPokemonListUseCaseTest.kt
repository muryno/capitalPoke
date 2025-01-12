package com.muryno.capital.feature.pokemon.domain.usecase

import com.muryno.capital.core.utils.Result
import com.muryno.capital.feature.pokemon.domain.model.PaginationParams
import com.muryno.capital.feature.pokemon.domain.model.Pokemon
import com.muryno.capital.feature.pokemon.domain.repository.PokemonRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetPokemonListUseCaseTest {

    private lateinit var repository: PokemonRepository
    private lateinit var useCase: GetPokemonListUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetPokemonListUseCase(repository)
    }

    @Test
    fun `invoke returns success with list of pokemon when repository succeeds`() = runTest {
        // Given
        val params = PaginationParams(page = 0)
        val pokemonList = listOf(
            Pokemon(id = 1, name = "Bulbasaur", imageUrl = "url1", height = 7),
            Pokemon(id = 2, name = "Ivysaur", imageUrl = "url2", height = 10)
        )
        coEvery { repository.getPokemonList(params) } returns Result.Success(pokemonList)

        // When
        val result = useCase(params)

        // Then
        assertEquals(Result.Success(pokemonList), result)
    }

    @Test
    fun `invoke returns error when repository fails`() = runTest {
        // Given
        val params = PaginationParams(page = 0)
        val error = Exception("Network error")
        coEvery { repository.getPokemonList(params) } returns Result.Error(error)

        // When
        val result = useCase(params)

        // Then
        assertEquals(Result.Error(error), result)
    }
} 