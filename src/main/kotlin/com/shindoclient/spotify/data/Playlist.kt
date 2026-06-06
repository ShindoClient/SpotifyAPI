package com.shindoclient.spotify.data

data class Playlist(
    val id: String,
    val name: String,
    val uri: String,
    val href: String,
    val description: String?,
    val images: List<Image>,
    val owner: User?,
    val isPublicAccess: Boolean?,
    val collaborative: Boolean,
    val tracks: List<PlaylistTrack>,
    val tracksTotal: Int,
    val followers: Followers?,
    val externalUrls: Map<String, String>,
    val snapshotId: String?,
)
