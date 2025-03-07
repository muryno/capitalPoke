package com.muryno.capital.feature.pokemon.data.model

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonNetworkItem>
) 