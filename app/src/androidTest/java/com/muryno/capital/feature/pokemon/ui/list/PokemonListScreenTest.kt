package com.muryno.capital.feature.pokemon.ui.list

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.muryno.capital.MainActivity
import com.muryno.capital.feature.pokemon.presentation.model.PokemonUiModel
import com.muryno.capital.feature.pokemon.presentation.viewmodel.PokemonListUiState
import com.muryno.capital.feature.pokemon.presentation.viewmodel.PokemonListViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PokemonListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val mockViewModel = mockk<PokemonListViewModel>(relaxed = true)
    private val uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun whenScreenIsLoading_showsLoadingIndicator() {
        // Given
        every { mockViewModel.uiState } returns uiState
        uiState.value = PokemonListUiState.Loading

        // When
        composeTestRule.setContent {
            PokemonListScreen(
                onPokemonClick = {},
                viewModel = mockViewModel
            )
        }

        // Then
        composeTestRule.onNodeWithTag("LoadingIndicator").assertExists()
    }

    @Test
    fun whenScreenHasData_showsPokemonList() {
        // Given
        val pokemons = listOf(
            PokemonUiModel(1, "Bulbasaur", "https://example.com/1.png", 0),
            PokemonUiModel(2, "Ivysaur", "https://example.com/2.png", 0)
        )
        every { mockViewModel.uiState } returns uiState
        uiState.value = PokemonListUiState.Success(pokemons)

        // When
        composeTestRule.setContent {
            PokemonListScreen(
                onPokemonClick = {},
                viewModel = mockViewModel
            )
        }

        // Then
        composeTestRule.onNodeWithTag("PokemonList").assertExists()
        composeTestRule.onNodeWithText("Bulbasaur").assertExists()
        composeTestRule.onNodeWithText("Ivysaur").assertExists()
    }

    @Test
    fun whenError_showsErrorMessage() {
        // Given
        val errorMessage = "Failed to load Pokemon"
        every { mockViewModel.uiState } returns uiState
        uiState.value = PokemonListUiState.Error(errorMessage)

        // When
        composeTestRule.setContent {
            PokemonListScreen(
                onPokemonClick = {},
                viewModel = mockViewModel
            )
        }

        // Then
        composeTestRule.onNodeWithText(errorMessage).assertExists()
    }

    @Test
    fun whenPokemonClicked_triggersCallback() {
        // Given
        val pokemons = listOf(
            PokemonUiModel(1, "Bulbasaur", "https://example.com/1.png", 0)
        )
        var clickedPokemonId: Int? = null
        every { mockViewModel.uiState } returns uiState
        uiState.value = PokemonListUiState.Success(pokemons)

        // When
        composeTestRule.setContent {
            PokemonListScreen(
                onPokemonClick = { clickedPokemonId = it },
                viewModel = mockViewModel
            )
        }

        composeTestRule.onNodeWithText("Bulbasaur").performClick()

        // Then
        assert(clickedPokemonId == 1)
    }
} 