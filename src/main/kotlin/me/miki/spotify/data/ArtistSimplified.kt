package me.miki.spotify.data

data class ArtistSimplified(
    val id: String,
    val name: String,
    val uri: String,
    val href: String,
    val externalUrls: Map<String, String>,
)
