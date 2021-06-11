package com.jamie.snippets.model

/**
 * Snippet class.
 *
 */
data class Snippet(
    val url: String,
    val name: String,
    var expires_at: Long,
    val snippet: String
) {
    var likes: Long = 0
}

