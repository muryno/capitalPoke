package com.muryno.capital.feature.pokemon.data.model

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val sprites: SpritesResponse
) 