package me.miki.spotify.mapper

import com.wrapper.spotify.model_objects.specification.User as SpotifyUser
import me.miki.spotify.data.Followers
import me.miki.spotify.data.User

internal fun SpotifyUser.toKotlin(): User = User(
    id = id,
    displayName = displayName,
    email = email,
    uri = uri,
    href = href,
    images = images?.toKotlinList() ?: emptyList(),
    followers = followers?.let { Followers(href = it.href, total = it.total) },
    country = country?.name,
    product = product?.name,
    externalUrls = externalUrls?.externalUrls?.mapValues { it.value } ?: emptyMap(),
)
