package me.miki.spotify.provider

import me.miki.spotify.Spotify
import me.miki.spotify.data.PlaylistSimplified
import me.miki.spotify.data.User
import me.miki.spotify.mapper.toKotlin
import java.util.concurrent.CompletableFuture

class UserProvider internal constructor(
    private val spotify: Spotify,
) {
    fun getCurrentUser(): CompletableFuture<User> = spotify.withRateLimit {
        spotify.api.getCurrentUsersProfile().build().execute().toKotlin()
    }

    fun getCurrentUserPlaylists(
        limit: Int = 50,
        offset: Int = 0,
    ): CompletableFuture<List<PlaylistSimplified>> = spotify.withRateLimit {
        spotify.api.getListOfCurrentUsersPlaylists()
            .limit(limit).offset(offset).build()
            .execute().items?.map { it.toKotlin() } ?: emptyList()
    }

    fun getUser(userId: String): CompletableFuture<User> = spotify.withRateLimit {
        spotify.api.getUsersProfile(userId).build().execute().toKotlin()
    }

    fun getUserPlaylists(
        userId: String,
        limit: Int = 50,
        offset: Int = 0,
    ): CompletableFuture<List<PlaylistSimplified>> = spotify.withRateLimit {
        spotify.api.getListOfUsersPlaylists(userId)
            .limit(limit).offset(offset).build()
            .execute().items?.map { it.toKotlin() } ?: emptyList()
    }
}
