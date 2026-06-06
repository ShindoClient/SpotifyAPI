package com.shindoclient.spotify.data

data class User(
    val id: String,
    val displayName: String?,
    val email: String?,
    val uri: String,
    val href: String,
    val images: List<Image>,
    val followers: Followers?,
    val country: String?,
    val product: String?,
    val externalUrls: Map<String, String>,
)
