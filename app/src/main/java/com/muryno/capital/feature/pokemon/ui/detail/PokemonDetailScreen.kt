package com.muryno.capital.feature.pokemon.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muryno.capital.core.ui.components.ErrorMessage
import com.muryno.capital.feature.pokemon.presentation.model.PokemonUiModel
import com.muryno.capital.feature.pokemon.presentation.viewmodel.PokemonDetailViewModel
import com.muryno.capital.feature.pokemon.presentation.viewmodel.PokemonDetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    pokemonId: Int,
    onBackClick: () -> Unit,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(pokemonId) {
        viewModel.getPokemonDetail(pokemonId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokemon Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is PokemonDetailUiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.testTag("LoadingIndicator")
                )
                is PokemonDetailUiState.Success -> PokemonDetailContent(
                    pokemon = state.pokemon,
                    modifier = Modifier.testTag("PokemonDetail")
                )
                is PokemonDetailUiState.Error -> ErrorMessage(message = state.message)
            }
        }
    }
}

@Composable
private fun PokemonDetailContent(
    pokemon: PokemonUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = pokemon.name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.testTag("PokemonName")
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        pokemon.height?.let { height ->
            Text(
                text = "Height: ${height / 10.0}m",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("PokemonHeight")
            )
        }
    }
} 