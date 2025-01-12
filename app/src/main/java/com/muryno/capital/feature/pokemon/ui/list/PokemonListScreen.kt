package com.muryno.capital.feature.pokemon.ui.list

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.muryno.capital.core.ui.components.ErrorMessage
import com.muryno.capital.core.ui.components.PullRefreshBox
import com.muryno.capital.feature.pokemon.presentation.viewmodel.PokemonListUiState
import com.muryno.capital.feature.pokemon.presentation.viewmodel.PokemonListViewModel

@Composable
fun PokemonListScreen(
    onPokemonClick: (Int) -> Unit,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    PullRefreshBox(
        isRefreshing = uiState is PokemonListUiState.Loading,
        onRefresh = viewModel::loadPokemonList
    ) {
        when (val state = uiState) {
            is PokemonListUiState.Loading -> CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .testTag("LoadingIndicator")
            )
            is PokemonListUiState.Success -> PokemonList(
                pokemons = state.pokemons,
                onPokemonClick = onPokemonClick,
                onLoadMore = viewModel::loadNextPage,
                modifier = Modifier.testTag("PokemonList")
            )
            is PokemonListUiState.Error -> ErrorMessage(message = state.message)
        }
    }
} 