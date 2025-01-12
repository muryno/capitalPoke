package com.muryno.capital.feature.pokemon.data.mapper

import com.muryno.capital.feature.pokemon.data.model.PokemonNetworkItem
import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonDetailsMapperTest {

    @Test
    fun `PokemonNetworkItem maps to domain Pokemon correctly`() {
        // Given
        val networkItem = PokemonNetworkItem(
            name = "bulbasaur",
            url = "https://pokeapi.co/api/v2/pokemon/1/"
        )

        // When
        val domain = networkItem.toDomain()

        // Then
        assertEquals(1, domain.id)
        assertEquals("bulbasaur", domain.name)
        assertEquals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", domain.imageUrl)
        assertEquals(0, domain.height)
    }

    @Test
    fun `PokemonNetworkItem with hyphenated name maps correctly`() {
        // Given
        val networkItem = PokemonNetworkItem(
            name = "mr-mime",
            url = "https://pokeapi.co/api/v2/pokemon/122/"
        )

        // When
        val domain = networkItem.toDomain()

        // Then
        assertEquals(122, domain.id)
        assertEquals("mr-mime", domain.name)
        assertEquals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/122.png", domain.imageUrl)
        assertEquals(0, domain.height)
    }
} 