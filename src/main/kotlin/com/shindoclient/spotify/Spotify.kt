package com.shindoclient.spotify

import com.wrapper.spotify.SpotifyApi
import com.wrapper.spotify.SpotifyHttpManager
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials
import com.shindoclient.spotify.auth.AuthToken
import com.shindoclient.spotify.auth.TokenStorage
import com.shindoclient.spotify.provider.*
import com.shindoclient.spotify.util.SpotifyRateLimiter
import java.net.URI
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import java.util.function.Supplier

/**
 * Main entry point for the Shindo Spotify library.
 *
 * Holds the SpotifyApi instance, manages authentication, and exposes
 * typed providers for every domain (search, user, playlist, album, artist, player).
 *
 * Usage:
 *   val spotify = Spotify(clientId, clientSecret, redirectUri)
 *   spotify.authenticate(code)  // after OAuth callback
 *   spotify.search.searchTracks("Never Gonna Give You Up")
 */
class Spotify(
    clientId: String,
    clientSecret: String,
    redirectUri: String,
    tokenStorage: TokenStorage? = null,
) {
    // ── API instance ─────────────────────────────────────────────────────

    @JvmField
    val api: SpotifyApi = SpotifyApi.Builder()
        .setClientId(clientId)
        .setClientSecret(clientSecret)
        .setRedirectUri(SpotifyHttpManager.makeUri(redirectUri))
        .build()

    // ── Rate limiter (shared across all providers) ───────────────────────

    @JvmField
    val rateLimiter = SpotifyRateLimiter(requestsPerSecond = 20.0)

    private val storage = tokenStorage

    // ── Providers (lazy — created on first access) ───────────────────────

    val search: SearchProvider by lazy { SearchProvider(this) }

    val user: UserProvider by lazy { UserProvider(this) }

    val playlist: PlaylistProvider by lazy { PlaylistProvider(this) }

    val album: AlbumProvider by lazy { AlbumProvider(this) }

    val artist: ArtistProvider by lazy { ArtistProvider(this) }

    val player: PlayerProvider by lazy { PlayerProvider(this) }

    // ── Authentication helpers ───────────────────────────────────────────

    /**
     * Exchange an authorization code for access + refresh tokens.
     */
    fun authenticate(code: String): CompletableFuture<AuthToken> =
        CompletableFuture.supplyAsync {
            try {
                val credentials: AuthorizationCodeCredentials = api.authorizationCode(code)
                    .build().execute()

                api.accessToken = credentials.accessToken
                api.refreshToken = credentials.refreshToken

                val token = AuthToken(
                    accessToken = credentials.accessToken,
                    refreshToken = credentials.refreshToken,
                    expiresIn = credentials.expiresIn,
                )
                storage?.save(token)
                token
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }

    /**
     * Authenticate using Client Credentials flow (no user context, no refresh token).
     */
    fun authenticateClient(): CompletableFuture<AuthToken> =
        CompletableFuture.supplyAsync {
            try {
                val credentials = api.clientCredentials().build().execute()
                api.accessToken = credentials.accessToken

                AuthToken(
                    accessToken = credentials.accessToken,
                    refreshToken = null,
                    expiresIn = credentials.expiresIn,
                )
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }

    /**
     * Refresh the access token using the stored refresh token.
     */
    fun refreshToken(): CompletableFuture<AuthToken> =
        CompletableFuture.supplyAsync {
            try {
                val credentials = api.authorizationCodeRefresh().build().execute()
                api.accessToken = credentials.accessToken
                credentials.refreshToken?.let { api.refreshToken = it }

                val token = AuthToken(
                    accessToken = credentials.accessToken,
                    refreshToken = credentials.refreshToken ?: api.refreshToken,
                    expiresIn = credentials.expiresIn,
                )
                storage?.save(token)
                token
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }

    /**
     * Restore a previously saved AuthToken (e.g. from file).
     */
    fun restoreToken(token: AuthToken) {
        api.accessToken = token.accessToken
        api.refreshToken = token.refreshToken
    }

    /**
     * Load stored token and refresh if available.
     * Returns true if a valid token was restored.
     */
    fun tryRestore(): Boolean {
        val saved = storage?.load() ?: return false
        restoreToken(saved)
        if (saved.refreshToken != null) {
            try {
                refreshToken().join()
            } catch (_: Exception) {
                return false
            }
        }
        return api.accessToken != null
    }

    // ── Rate-limited async execution ─────────────────────────────────────

    /**
     * Runs [block] on a background thread, acquiring the rate limiter first.
     */
    fun <T> withRateLimit(block: () -> T): CompletableFuture<T> =
        CompletableFuture.supplyAsync {
            rateLimiter.acquire()
            try {
                block()
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }

    /**
     * Helper: runs [block] on the common pool without rate limiting.
     */
    fun <T> async(block: () -> T): CompletableFuture<T> =
        CompletableFuture.supplyAsync(Supplier { block() })
}
