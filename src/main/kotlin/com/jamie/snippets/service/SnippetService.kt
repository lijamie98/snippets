package com.jamie.snippets.service

import com.jamie.snippets.exceptions.AlreadyExistsException
import com.jamie.snippets.exceptions.NotFoundException
import com.jamie.snippets.model.Snippet
import org.springframework.stereotype.Service

/**
 * Snippet service takes care of the snippet create/read/like operations.
 *
 * @constructor Create empty Snippet service
 */
@Service
class SnippetService () {
    var snippets = HashMap<String, Snippet>()

    fun createSnippet(snippetId: String, snippet: Snippet) : Snippet {
        if (snippets[snippetId] != null)
            throw AlreadyExistsException("snippet already exists.")
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
