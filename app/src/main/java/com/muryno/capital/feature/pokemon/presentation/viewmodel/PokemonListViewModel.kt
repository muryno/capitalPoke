package com.muryno.capital.feature.pokemon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muryno.capital.feature.pokemon.domain.model.PaginationParams
import com.muryno.capital.feature.pokemon.domain.usecase.GetPokemonListUseCase
import com.muryno.capital.feature.pokemon.presentation.mapper.toUiModel
import com.muryno.capital.feature.pokemon.presentation.model.PokemonUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    private var currentPage = 0
    private var isLoading = false

    init {
        loadPokemonList()
    }

    fun loadPokemonList() {
        if (isLoading) return
        currentPage = 0
        fetchPokemon(isRefresh = true)
    }

    fun loadNextPage() {
        if (isLoading) return
        currentPage++
        fetchPokemon(isRefresh = false)
    }

    private fun fetchPokemon(isRefresh: Boolean) {
        viewModelScope.launch {
            isLoading = true
            if (isRefresh) {
                _uiState.value = PokemonListUiState.Loading
            }
            
            getPokemonListUseCase(PaginationParams(currentPage)).fold(
                onSuccess = { pokemonList ->
                    val currentList = if (isRefresh) {
                        emptyList()
                    } else {
                        (uiState.value as? PokemonListUiState.Success)?.pokemons ?: emptyList()
                    }
                    _uiState.value = PokemonListUiState.Success(
                        currentList + pokemonList.map { it.toUiModel() }
                    )
                },
                onError = { error ->
                    _uiState.value = PokemonListUiState.Error(error.message ?: "Unknown error occurred")
                }
            )
            isLoading = false
        }
    }

    @VisibleForTesting
    fun updateState(newState: PokemonListUiState) {
        _uiState.value = newState
    }
}

sealed class PokemonListUiState {
    data object Loading : PokemonListUiState()
    data class Success(val pokemons: List<PokemonUiModel>) : PokemonListUiState()
    data class Error(val message: String) : PokemonListUiState()
} 