package com.muryno.capital.feature.pokemon.data.mapper

import com.muryno.capital.feature.pokemon.data.model.PokemonDetailResponse
import com.muryno.capital.feature.pokemon.data.model.SpritesResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonListMapperTest {

    @Test
    fun `PokemonDetailResponse maps to domain Pokemon correctly`() {
        // Given
        val response = PokemonDetailResponse(
            id = 1,
            name = "bulbasaur",
            height = 7,
            sprites = SpritesResponse("https://example.com/bulbasaur.png")
        )

        // When
        val domain = response.toDomain()

        // Then
        assertEquals(1, domain.id)
        assertEquals("bulbasaur", domain.name)
        assertEquals("https://example.com/bulbasaur.png", domain.imageUrl)
        assertEquals(7, domain.height)
    }

    @Test
    fun `PokemonDetailResponse with null sprites maps to domain Pokemon with empty imageUrl`() {
        // Given
        val response = PokemonDetailResponse(
            id = 1,
            name = "bulbasaur",
            height = 7,
            sprites = SpritesResponse(null)
        )

        // When
        val domain = response.toDomain()

        // Then
        assertEquals(1, domain.id)
        assertEquals("bulbasaur", domain.name)
        assertEquals("", domain.imageUrl)
        assertEquals(7, domain.height)
    }
} 