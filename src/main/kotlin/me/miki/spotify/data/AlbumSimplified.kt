package me.miki.spotify.data

data class AlbumSimplified(
    val id: String,
    val name: String,
    val uri: String,
    val href: String,
    val albumType: String,
    val artists: List<ArtistSimplified>,
    val images: List<Image>,
    val releaseDate: String?,
    val releaseDatePrecision: String?,
    val externalUrls: Map<String, String>,
)
