package me.miki.spotify.data

data class Category(
    val id: String,
    val name: String,
    val href: String,
    val icons: List<Image>,
)
