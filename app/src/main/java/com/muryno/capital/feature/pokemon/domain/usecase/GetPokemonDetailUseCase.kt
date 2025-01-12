package com.muryno.capital.feature.pokemon.domain.usecase

import com.muryno.capital.core.utils.Result
import com.muryno.capital.feature.pokemon.domain.model.Pokemon
import com.muryno.capital.feature.pokemon.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(id: Int): Result<Pokemon> {
        return repository.getPokemonDetail(id)
    }
} 