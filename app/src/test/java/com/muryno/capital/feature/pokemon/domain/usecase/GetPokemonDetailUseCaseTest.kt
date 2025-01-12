package com.muryno.capital.feature.pokemon.domain.usecase

import com.muryno.capital.core.utils.Result
import com.muryno.capital.feature.pokemon.domain.model.Pokemon
import com.muryno.capital.feature.pokemon.domain.repository.PokemonRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetPokemonDetailUseCaseTest {

    private lateinit var repository: PokemonRepository
    private lateinit var useCase: GetPokemonDetailUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetPokemonDetailUseCase(repository)
    }

    @Test
    fun `invoke returns success with pokemon details when repository succeeds`() = runTest {
        // Given
        val pokemonId = 1
        val pokemon = Pokemon(
            id = pokemonId,
            name = "bulbasaur",
            imageUrl = "https://example.com/bulbasaur.png",
            height = 7
        )
        coEvery { repository.getPokemonDetail(pokemonId) } returns Result.Success(pokemon)

        // When
        val result = useCase(pokemonId)

        // Then
        require(result is Result.Success)
        assertEquals(pokemon, result.data)
        coVerify(exactly = 1) { repository.getPokemonDetail(pokemonId) }
    }

    @Test
    fun `invoke returns error when repository fails`() = runTest {
        // Given
        val pokemonId = 1
        val error = Exception("Failed to fetch Pokemon details")
        coEvery { repository.getPokemonDetail(pokemonId) } returns Result.Error(error)

        // When
        val result = useCase(pokemonId)

        // Then
        require(result is Result.Error)
        assertEquals(error, result.exception)
        coVerify(exactly = 1) { repository.getPokemonDetail(pokemonId) }
    }

    @Test
    fun `invoke with invalid pokemon id returns error`() = runTest {
        // Given
        val invalidPokemonId = -1
        val error = IllegalArgumentException("Invalid Pokemon ID")
        coEvery { repository.getPokemonDetail(invalidPokemonId) } returns Result.Error(error)

        // When
        val result = useCase(invalidPokemonId)

        // Then
        require(result is Result.Error)
        assertEquals(error, result.exception)
        coVerify(exactly = 1) { repository.getPokemonDetail(invalidPokemonId) }
    }
} 