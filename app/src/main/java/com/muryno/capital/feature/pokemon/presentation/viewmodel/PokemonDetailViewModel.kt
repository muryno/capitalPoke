package com.muryno.capital.feature.pokemon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muryno.capital.core.utils.Result
import com.muryno.capital.feature.pokemon.domain.usecase.GetPokemonDetailUseCase
import com.muryno.capital.feature.pokemon.presentation.mapper.toUiModel
import com.muryno.capital.feature.pokemon.presentation.model.PokemonUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Loading)
    val uiState: StateFlow<PokemonDetailUiState> = _uiState

    fun getPokemonDetail(id: Int) {
        viewModelScope.launch {
            _uiState.value = PokemonDetailUiState.Loading
            when (val result = getPokemonDetailUseCase(id)) {
                is Result.Success -> {
                    _uiState.value = PokemonDetailUiState.Success(result.data.toUiModel())
                }
                is Result.Error -> {
                    _uiState.value = PokemonDetailUiState.Error(
                        result.exception.message ?: "Unknown error occurred"
                    )
                }
            }
        }
    }
}

sealed class PokemonDetailUiState {
    object Loading : PokemonDetailUiState()
    data class Success(val pokemon: PokemonUiModel) : PokemonDetailUiState()
    data class Error(val message: String) : PokemonDetailUiState()
} 