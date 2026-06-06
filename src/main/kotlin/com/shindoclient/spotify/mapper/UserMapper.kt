package com.shindoclient.spotify.mapper

import com.wrapper.spotify.model_objects.specification.User as SpotifyUser
import com.shindoclient.spotify.data.Followers
import com.shindoclient.spotify.data.User

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
