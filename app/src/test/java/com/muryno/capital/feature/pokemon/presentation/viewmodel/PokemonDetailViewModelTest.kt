package com.muryno.capital.feature.pokemon.presentation.viewmodel

import com.muryno.capital.core.utils.Result
import com.muryno.capital.feature.pokemon.domain.model.Pokemon
import com.muryno.capital.feature.pokemon.domain.usecase.GetPokemonDetailUseCase
import com.muryno.capital.feature.pokemon.presentation.mapper.toUiModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonDetailViewModelTest {

    private lateinit var useCase: GetPokemonDetailUseCase
    private lateinit var viewModel: PokemonDetailViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        useCase = mockk(relaxed = true)
        viewModel = PokemonDetailViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        assertEquals(PokemonDetailUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `getPokemonDetail success updates state to Success with pokemon details`() = runTest {
        // Given
        val pokemonId = 1
        val pokemon = Pokemon(id = pokemonId, name = "Bulbasaur", imageUrl = "url", height = 7)
        coEvery { useCase(pokemonId) } returns Result.Success(pokemon)

        // When
        viewModel.getPokemonDetail(pokemonId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(
            PokemonDetailUiState.Success(pokemon.toUiModel()),
            viewModel.uiState.value
        )
        coVerify(exactly = 1) { useCase(pokemonId) }
    }

    @Test
    fun `getPokemonDetail error updates state to Error`() = runTest {
        // Given
        val pokemonId = 1
        val error = Exception("Failed to load Pokemon details")
        coEvery { useCase(pokemonId) } returns Result.Error(error)

        // When
        viewModel.getPokemonDetail(pokemonId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(
            PokemonDetailUiState.Error("Failed to load Pokemon details"),
            viewModel.uiState.value
        )
        coVerify(exactly = 1) { useCase(pokemonId) }
    }

    @Test
    fun `getPokemonDetail shows loading state while fetching data`() = runTest {
        // Given
        val pokemonId = 1
        val pokemon = Pokemon(id = pokemonId, name = "Bulbasaur", imageUrl = "url", height = 7)
        coEvery { useCase(pokemonId) } returns Result.Success(pokemon)

        // When
        viewModel.getPokemonDetail(pokemonId)
        
        // Then - verify loading state before advancing time
        assertEquals(PokemonDetailUiState.Loading, viewModel.uiState.value)
        
        // When - advance time to complete the coroutine
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then - verify success state after completion
        assertEquals(
            PokemonDetailUiState.Success(pokemon.toUiModel()),
            viewModel.uiState.value
        )
    }
} 