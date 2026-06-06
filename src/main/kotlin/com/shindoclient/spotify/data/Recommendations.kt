package com.shindoclient.spotify.data

data class Recommendations(
    val seeds: List<RecommendationSeed>,
    val tracks: List<Track>,
)

data class RecommendationSeed(
    val id: String,
    val type: String,
    val href: String?,
    val initialPoolSize: Int,
    val afterFilteringSize: Int,
    val afterRelinkingSize: Int,
)
