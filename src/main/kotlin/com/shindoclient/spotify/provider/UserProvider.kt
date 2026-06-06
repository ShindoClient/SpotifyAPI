package com.shindoclient.spotify.provider

import com.shindoclient.spotify.Spotify
import com.shindoclient.spotify.data.PlaylistSimplified
import com.shindoclient.spotify.data.User
import com.shindoclient.spotify.mapper.toKotlin
import java.util.concurrent.CompletableFuture

class UserProvider internal constructor(
    private val spotify: Spotify,
) {
    fun getCurrentUser(): CompletableFuture<User> = spotify.withRateLimit {
        safeCall { spotify.api.getCurrentUsersProfile().build().execute() }?.toKotlin()
            ?: throw IllegalStateException("Failed to get current user")
    }

    fun getCurrentUserPlaylists(
        limit: Int = 50,
        offset: Int = 0,
    ): CompletableFuture<List<PlaylistSimplified>> = spotify.withRateLimit {
        safeItems {
            spotify.api.getListOfCurrentUsersPlaylists()
                .limit(limit).offset(offset).build()
                .execute()?.items
        }.map { it.toKotlin() }
    }

    fun getUser(userId: String): CompletableFuture<User> = spotify.withRateLimit {
        safeCall { spotify.api.getUsersProfile(userId).build().execute() }?.toKotlin()
            ?: throw IllegalStateException("Failed to get user: $userId")
    }

    fun getUserPlaylists(
        userId: String,
        limit: Int = 50,
        offset: Int = 0,
    ): CompletableFuture<List<PlaylistSimplified>> = spotify.withRateLimit {
        safeItems {
            spotify.api.getListOfUsersPlaylists(userId)
                .limit(limit).offset(offset).build()
                .execute()?.items
        }.map { it.toKotlin() }
    }
}
