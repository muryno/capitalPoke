package com.muryno.capital.feature.pokemon.domain.model

data class PaginationParams(
    val page: Int,
    val pageSize: Int = DEFAULT_PAGE_SIZE
) {
    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
} 