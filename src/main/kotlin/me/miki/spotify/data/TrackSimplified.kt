package me.miki.spotify.data

data class TrackSimplified(
    val id: String,
    val name: String,
    val uri: String,
    val href: String,
    val durationMs: Int,
    val trackNumber: Int,
    val discNumber: Int,
    val artists: List<ArtistSimplified>,
    val isExplicit: Boolean,
    val externalUrls: Map<String, String>,
    val isPlayable: Boolean?,
)
