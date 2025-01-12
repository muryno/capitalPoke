package com.muryno.capital.feature.pokemon.data.repository

import com.muryno.capital.core.utils.Result
import com.muryno.capital.feature.pokemon.data.mapper.toDomain
import com.muryno.capital.feature.pokemon.data.remote.PokemonApi
import com.muryno.capital.feature.pokemon.domain.model.PaginationParams
import com.muryno.capital.feature.pokemon.domain.model.Pokemon
import com.muryno.capital.feature.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi
) : PokemonRepository {
    override suspend fun getPokemonList(params: PaginationParams): Result<List<Pokemon>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getPokemonList(
                    offset = params.page * params.pageSize,
                    limit = params.pageSize
                )
                Result.Success(response.results.map { it.toDomain() })
            } catch (e: IOException) {
                Result.Error(e)
            } catch (e: HttpException) {
                Result.Error(e)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getPokemonDetail(id: Int): Result<Pokemon> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getPokemonDetail(id)
                Result.Success(response.toDomain())
            } catch (e: IOException) {
                Result.Error(e)
            } catch (e: HttpException) {
                Result.Error(e)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
} 