package com.jamie.quickweb.api

import org.jetbrains.annotations.NotNull

data class SnippetRequest (
    @NotNull
    val name: String,
    @NotNull
    val expires_in: Long,
    @NotNull
    val snippet: String
)

data class SnippetResponse (
    @NotNull
    val url: String,
    @NotNull
    val name: String,
    @NotNull
    val expires_at: String,
    @NotNull
    val snippet: String,
    @NotNull
    val likes: Long
)
