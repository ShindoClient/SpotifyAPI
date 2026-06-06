package me.miki.spotify.data

data class Album(
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
    val genres: List<String>,
    val label: String?,
    val popularity: Int,
    val copyrights: List<Copyright>,
    val tracks: List<TrackSimplified>,
)

data class Copyright(
    val text: String,
    val type: String,
)
