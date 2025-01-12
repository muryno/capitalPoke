package com.muryno.capital.feature.pokemon.data.mapper

import com.muryno.capital.feature.pokemon.data.model.PokemonNetworkItem
import com.muryno.capital.feature.pokemon.domain.model.Pokemon

fun PokemonNetworkItem.toDomain(): Pokemon {
    val id = url.split("/").dropLast(1).last().toInt()
    return Pokemon(
        id = id,
        name = name,
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
        height = 0
    )
}