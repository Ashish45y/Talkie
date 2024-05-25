package dev.ashish.talkie.data.model

data class MoviesResponse(
    val page: Int,
    val results: List<MoviesDto> = ArrayList(),
    val total_pages: Int,
    val total_results: Int
)
