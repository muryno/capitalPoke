package com.muryno.capital.feature.pokemon.domain.usecase

import com.muryno.capital.core.utils.Result
import com.muryno.capital.feature.pokemon.domain.model.PaginationParams
import com.muryno.capital.feature.pokemon.domain.model.Pokemon
import com.muryno.capital.feature.pokemon.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(paginationParams: PaginationParams): Result<List<Pokemon>> {
        return repository.getPokemonList(paginationParams)
    }
} 