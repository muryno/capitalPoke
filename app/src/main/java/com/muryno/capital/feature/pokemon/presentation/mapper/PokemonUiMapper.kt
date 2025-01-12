package com.muryno.capital.feature.pokemon.presentation.mapper

import com.muryno.capital.feature.pokemon.domain.model.Pokemon
import com.muryno.capital.feature.pokemon.presentation.model.PokemonUiModel

fun Pokemon.toUiModel(): PokemonUiModel {
    return PokemonUiModel(
        id = id,
        name = name.split("-").joinToString("-") { it.replaceFirstChar { char -> char.uppercase() } },
        imageUrl = imageUrl,
        height = height
    )
}

