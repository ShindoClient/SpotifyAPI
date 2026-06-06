package com.shindoclient.spotify.util

/**
 * Simple token-bucket rate limiter for Spotify API requests.
 */
class SpotifyRateLimiter(
    private val requestsPerSecond: Double = 20.0,
) {
    private val minIntervalMs = (1000.0 / requestsPerSecond).toLong()
    private var lastRequestTime = 0L

    @Synchronized
    fun tryAcquire(): Boolean {
        val now = System.currentTimeMillis()
        if (now - lastRequestTime >= minIntervalMs) {
            lastRequestTime = now
            return true
        }
        return false
    }

    @Synchronized
    fun acquire() {
        val now = System.currentTimeMillis()
        val elapsed = now - lastRequestTime
        if (elapsed < minIntervalMs) {
            Thread.sleep(minIntervalMs - elapsed)
        }
        lastRequestTime = System.currentTimeMillis()
    }
}
