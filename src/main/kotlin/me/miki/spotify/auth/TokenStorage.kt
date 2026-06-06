package me.miki.spotify.auth

/**
 * Abstraction for persisting Spotify auth tokens.
 * Implementations can store tokens in files, Preferences, etc.
 */
interface TokenStorage {
    fun load(): AuthToken?
    fun save(token: AuthToken)
    fun clear()
}
