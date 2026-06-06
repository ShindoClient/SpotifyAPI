package me.miki.spotify.mapper

import com.wrapper.spotify.model_objects.specification.Image as SpotifyImage
import me.miki.spotify.data.Image

internal fun SpotifyImage.toKotlin(): Image = Image(
    url = url,
    width = width?.let { it },
    height = height?.let { it },
)

internal fun Array<out SpotifyImage>.toKotlinList(): List<Image> =
    map { it.toKotlin() }
