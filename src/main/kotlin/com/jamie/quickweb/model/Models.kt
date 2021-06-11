package com.jamie.quickweb.model

data class Snippet(
    val url: String,
    val name: String,
    var expires_at: Long,
    val snippet: String
) {
    var likes: Long = 0
}

