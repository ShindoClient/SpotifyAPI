package com.shindoclient.spotify.data

data class PlaylistSimplified(
    val id: String,
    val name: String,
    val uri: String,
    val href: String,
    val images: List<Image>,
    val owner: User?,
    val isPublicAccess: Boolean?,
    val collaborative: Boolean,
    val tracksTotal: Int,
    val externalUrls: Map<String, String>,
    val snapshotId: String?,
)
