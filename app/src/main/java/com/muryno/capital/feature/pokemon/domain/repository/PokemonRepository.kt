package com.muryno.capital.feature.pokemon.domain.repository

import com.muryno.capital.core.utils.Result
import com.muryno.capital.feature.pokemon.domain.model.PaginationParams
import com.muryno.capital.feature.pokemon.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemonList(params: PaginationParams): Result<List<Pokemon>>
    suspend fun getPokemonDetail(id: Int): Result<Pokemon>
} 