package com.muryno.capital.feature.pokemon.presentation.mapper

import com.muryno.capital.feature.pokemon.domain.model.Pokemon
import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonUiMapperTest {

    @Test
    fun `Pokemon maps to PokemonUiModel correctly with all fields`() {
        // Given
        val pokemon = Pokemon(
            id = 1,
            name = "bulbasaur",
            imageUrl = "https://example.com/bulbasaur.png",
            height = 7
        )

        // When
        val uiModel = pokemon.toUiModel()

        // Then
        assertEquals(1, uiModel.id)
        assertEquals("Bulbasaur", uiModel.name)
        assertEquals("https://example.com/bulbasaur.png", uiModel.imageUrl)
        assertEquals(7, uiModel.height)
    }



    @Test
    fun `Pokemon name is capitalized in PokemonUiModel`() {
        // Given
        val pokemon = Pokemon(
            id = 1,
            name = "mr-mime",
            imageUrl = "",
            height = 0
        )

        // When
        val uiModel = pokemon.toUiModel()

        // Then
        assertEquals("Mr-Mime", uiModel.name)
    }
} 