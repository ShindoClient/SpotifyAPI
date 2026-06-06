package me.miki.spotify.data

data class SearchResults(
    val tracks: List<Track>,
    val albums: List<AlbumSimplified>,
    val artists: List<Artist>,
    val playlists: List<PlaylistSimplified>,
) {
    companion object {
        val EMPTY = SearchResults(
            tracks = emptyList(),
            albums = emptyList(),
            artists = emptyList(),
            playlists = emptyList(),
        )
    }
}
