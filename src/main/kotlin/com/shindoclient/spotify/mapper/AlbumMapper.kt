package com.shindoclient.spotify.mapper

import com.wrapper.spotify.model_objects.specification.Album as SpotifyAlbum
import com.wrapper.spotify.model_objects.specification.AlbumSimplified as SpotifyAlbumSimplified
import com.shindoclient.spotify.data.Album
import com.shindoclient.spotify.data.AlbumSimplified
import com.shindoclient.spotify.data.Copyright

internal fun SpotifyAlbumSimplified.toKotlin(): AlbumSimplified = AlbumSimplified(
    id = id,
    name = name,
    uri = uri,
    href = href,
    albumType = albumType.type,
    artists = artists?.map { it.toKotlin() } ?: emptyList(),
    images = images?.toKotlinList() ?: emptyList(),
    releaseDate = releaseDate,
    releaseDatePrecision = releaseDatePrecision?.precision,
    externalUrls = externalUrls?.externalUrls?.mapValues { it.value } ?: emptyMap(),
)

internal fun SpotifyAlbum.toKotlin(): Album = Album(
    id = id,
    name = name,
    uri = uri,
    href = href,
    albumType = albumType.type,
    artists = artists?.map { it.toKotlin() } ?: emptyList(),
    images = images?.toKotlinList() ?: emptyList(),
    releaseDate = releaseDate,
    releaseDatePrecision = releaseDatePrecision?.precision,
    externalUrls = externalUrls?.externalUrls?.mapValues { it.value } ?: emptyMap(),
    genres = genres?.toList() ?: emptyList(),
    label = label,
    popularity = popularity,
    copyrights = copyrights?.map { Copyright(text = it.text, type = it.type.name) } ?: emptyList(),
    tracks = tracks?.items?.map { it.toKotlin() }?.toList() ?: emptyList(),
)
