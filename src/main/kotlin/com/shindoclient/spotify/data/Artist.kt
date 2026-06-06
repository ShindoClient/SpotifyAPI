package com.shindoclient.spotify.data

data class Artist(
    val id: String,
    val name: String,
    val uri: String,
    val href: String,
    val externalUrls: Map<String, String>,
    val genres: List<String>,
    val images: List<Image>,
    val followers: Followers?,
    val popularity: Int,
)
