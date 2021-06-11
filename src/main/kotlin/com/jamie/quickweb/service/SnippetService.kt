package com.jamie.quickweb.service

import com.jamie.quickweb.exceptions.NotFoundException
import com.jamie.quickweb.model.Snippet
import org.springframework.stereotype.Service

@Service
class SnippetService () {
    var snippets = HashMap<String, Snippet>()

    fun createSnippet(snippetId: String, snippet: Snippet) : Snippet {
        snippets[snippetId] = snippet
        return snippet
    }

    fun readSnippet(snippetId: String) : Snippet {
        var snippet = snippets[snippetId] ?: throw NotFoundException("Snippet not found.")
        snippet.expires_at += 30
        return snippet
    }

    fun like(snippetId: String) : Snippet {
        var snippet = snippets[snippetId] ?: throw NotFoundException("Snippet not found.")
        snippet.expires_at = snippet.expires_at + 30
        snippet.likes += 1
        return snippet
    }
}
