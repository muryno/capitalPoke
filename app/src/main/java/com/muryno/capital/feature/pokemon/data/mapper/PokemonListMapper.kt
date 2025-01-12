package com.muryno.capital.feature.pokemon.data.mapper

import com.muryno.capital.feature.pokemon.data.model.PokemonDetailResponse
import com.muryno.capital.feature.pokemon.domain.model.Pokemon

fun PokemonDetailResponse.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        imageUrl = sprites.front_default ?: "",
        height = height
    )
} 