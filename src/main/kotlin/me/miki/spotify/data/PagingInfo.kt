package me.miki.spotify.data

data class PagingInfo(
    val total: Int,
    val limit: Int,
    val offset: Int,
    val next: String?,
    val previous: String?,
)
