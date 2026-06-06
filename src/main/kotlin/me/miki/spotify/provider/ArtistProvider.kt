package me.miki.spotify.provider

import com.neovisionaries.i18n.CountryCode
import me.miki.spotify.Spotify
import me.miki.spotify.data.AlbumSimplified
import me.miki.spotify.data.Artist
import me.miki.spotify.data.Track
import me.miki.spotify.mapper.toKotlin
import java.util.concurrent.CompletableFuture

class ArtistProvider internal constructor(
    private val spotify: Spotify,
) {
    fun getArtist(artistId: String): CompletableFuture<Artist> = spotify.withRateLimit {
        spotify.api.getArtist(artistId).build().execute().toKotlin()
    }

    fun getSeveralArtists(artistIds: List<String>): CompletableFuture<List<Artist>> = spotify.withRateLimit {
        spotify.api.getSeveralArtists(*artistIds.toTypedArray()).build()
            .execute().map { it.toKotlin() }.toList()
    }

    fun getTopTracks(
        artistId: String,
        country: CountryCode = CountryCode.US,
    ): CompletableFuture<List<Track>> = spotify.withRateLimit {
        spotify.api.getArtistsTopTracks(artistId, country).build()
            .execute().map { it.toKotlin() }.toList()
    }

    fun getAlbums(
        artistId: String,
        limit: Int = 20,
        offset: Int = 0,
    ): CompletableFuture<List<AlbumSimplified>> = spotify.withRateLimit {
        spotify.api.getArtistsAlbums(artistId).limit(limit).offset(offset).build()
            .execute().items?.map { it.toKotlin() }?.toList() ?: emptyList()
    }

    fun getRelatedArtists(artistId: String): CompletableFuture<List<Artist>> = spotify.withRateLimit {
        spotify.api.getArtistsRelatedArtists(artistId).build()
            .execute().map { it.toKotlin() }.toList()
    }
}
