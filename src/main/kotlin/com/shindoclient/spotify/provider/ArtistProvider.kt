package com.shindoclient.spotify.provider

import com.neovisionaries.i18n.CountryCode
import com.shindoclient.spotify.Spotify
import com.shindoclient.spotify.data.AlbumSimplified
import com.shindoclient.spotify.data.Artist
import com.shindoclient.spotify.data.Track
import com.shindoclient.spotify.mapper.toKotlin
import java.util.concurrent.CompletableFuture

class ArtistProvider internal constructor(
    private val spotify: Spotify,
) {
    fun getArtist(artistId: String): CompletableFuture<Artist> = spotify.withRateLimit {
        safeCall { spotify.api.getArtist(artistId).build().execute() }?.toKotlin()
            ?: throw IllegalStateException("Failed to get artist: $artistId")
    }

    fun getSeveralArtists(artistIds: List<String>): CompletableFuture<List<Artist>> = spotify.withRateLimit {
        safeCall {
            spotify.api.getSeveralArtists(*artistIds.toTypedArray()).build().execute()
        }?.map { it.toKotlin() }?.toList().orEmpty()
    }

    fun getTopTracks(
        artistId: String,
        country: CountryCode = CountryCode.US,
    ): CompletableFuture<List<Track>> = spotify.withRateLimit {
        safeCall {
            spotify.api.getArtistsTopTracks(artistId, country).build().execute()
        }?.map { it.toKotlin() }?.toList().orEmpty()
    }

    fun getAlbums(
        artistId: String,
        limit: Int = 20,
        offset: Int = 0,
    ): CompletableFuture<List<AlbumSimplified>> = spotify.withRateLimit {
        safeItems {
            spotify.api.getArtistsAlbums(artistId).limit(limit).offset(offset).build()
                .execute()?.items
        }.map { it.toKotlin() }
    }

    fun getRelatedArtists(artistId: String): CompletableFuture<List<Artist>> = spotify.withRateLimit {
        safeCall {
            spotify.api.getArtistsRelatedArtists(artistId).build().execute()
        }?.map { it.toKotlin() }?.toList().orEmpty()
    }
}
