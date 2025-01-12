package com.muryno.capital.feature.pokemon.presentation.viewmodel

import com.muryno.capital.core.utils.Result
import com.muryno.capital.feature.pokemon.domain.model.PaginationParams
import com.muryno.capital.feature.pokemon.domain.model.Pokemon
import com.muryno.capital.feature.pokemon.domain.usecase.GetPokemonListUseCase
import com.muryno.capital.feature.pokemon.presentation.mapper.toUiModel
import io.mockk.coEvery
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
class PokemonListViewModelTest {

    private lateinit var useCase: GetPokemonListUseCase
    private lateinit var viewModel: PokemonListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        useCase = mockk(relaxed = true)
        viewModel = PokemonListViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        assertEquals(PokemonListUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `loadPokemonList success updates state to Success with pokemon list`() = runTest {
        // Given
        val pokemonList = listOf(
            Pokemon(id = 1, name = "Bulbasaur", imageUrl = "url1", height = 7),
            Pokemon(id = 2, name = "Ivysaur", imageUrl = "url2", height = 10)
        )
        coEvery { useCase.invoke(any()) } returns Result.Success(pokemonList)

        // When
        viewModel.loadPokemonList()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(
            PokemonListUiState.Success(pokemonList.map { it.toUiModel() }),
            viewModel.uiState.value
        )
    }

    @Test
    fun `loadPokemonList error updates state to Error`() = runTest {
        // Given
        val error = Exception("Network error")
        coEvery { useCase.invoke(any()) } returns Result.Error(error)

        // When
        viewModel.loadPokemonList()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(
            PokemonListUiState.Error("Network error"),
            viewModel.uiState.value
        )
    }

    @Test
    fun `loadNextPage appends new pokemon to existing list`() = runTest {
        // Given
        val initialList = listOf(
            Pokemon(id = 1, name = "Bulbasaur", imageUrl = "url1", height = 7)
        )
        val nextPageList = listOf(
            Pokemon(id = 2, name = "Ivysaur", imageUrl = "url2", height = 10)
        )
        
        coEvery { useCase.invoke(match { it.page == 0 }) } returns Result.Success(initialList)
        coEvery { useCase.invoke(match { it.page == 1 }) } returns Result.Success(nextPageList)

        // When
        viewModel.loadPokemonList() // Load initial page
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.loadNextPage() // Load next page
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(
            PokemonListUiState.Success((initialList + nextPageList).map { it.toUiModel() }),
            viewModel.uiState.value
        )
    }
} 