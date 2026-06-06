package me.miki.spotify.data

data class Track(
    val id: String,
    val name: String,
    val uri: String,
    val href: String,
    val durationMs: Int,
    val trackNumber: Int,
    val discNumber: Int,
    val artists: List<ArtistSimplified>,
    val album: AlbumSimplified?,
    val isExplicit: Boolean,
    val popularity: Int,
    val externalUrls: Map<String, String>,
    val previewUrl: String?,
    val isPlayable: Boolean?,
)
