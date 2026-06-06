package com.shindoclient.spotify.mapper

import com.wrapper.spotify.model_objects.specification.Category as SpotifyCategory
import com.shindoclient.spotify.data.Category

internal fun SpotifyCategory.toKotlin(): Category = Category(
    id = id,
    name = name,
    href = href,
    icons = icons?.toKotlinList() ?: emptyList(),
)
