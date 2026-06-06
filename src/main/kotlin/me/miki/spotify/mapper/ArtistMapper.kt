package me.miki.spotify.mapper

import com.wrapper.spotify.model_objects.specification.Artist as SpotifyArtist
import com.wrapper.spotify.model_objects.specification.ArtistSimplified as SpotifyArtistSimplified
import me.miki.spotify.data.Artist
import me.miki.spotify.data.ArtistSimplified
import me.miki.spotify.data.Followers

internal fun SpotifyArtistSimplified.toKotlin(): ArtistSimplified = ArtistSimplified(
    id = id,
    name = name,
    uri = uri,
    href = href,
    externalUrls = externalUrls?.externalUrls?.mapValues { it.value } ?: emptyMap(),
)

internal fun SpotifyArtist.toKotlin(): Artist = Artist(
    id = id,
    name = name,
    uri = uri,
    href = href,
    externalUrls = externalUrls?.externalUrls?.mapValues { it.value } ?: emptyMap(),
    genres = genres?.toList() ?: emptyList(),
    images = images?.toKotlinList() ?: emptyList(),
    followers = followers?.let { Followers(href = it.href, total = it.total) },
    popularity = popularity,
)
