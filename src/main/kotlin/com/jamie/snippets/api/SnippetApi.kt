package com.jamie.snippets.api

import com.jamie.snippets.model.Snippet
import com.jamie.snippets.service.SnippetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URL
import java.time.Instant
import java.time.LocalDateTime
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * Snippet api controller.
 *
 * @property snippetService The service that takes care of the snippet operations.
 * @constructor
 */
@RestController
class SnippetApi(
    @Autowired private val snippetService: SnippetService
) {
    @PostMapping("/snippets")
    fun createSnippet(@RequestBody snippetRequest: SnippetRequest, request: HttpServletRequest): ResponseEntity<SnippetResponse> {
        val url = URL(request.requestURL.toString())
        val path = url.path

        var snippet = fromRequest(snippetRequest, url.toString())
        snippet.expires_at += 30
        snippet = snippetService.createSnippet(path + "/" + snippet.name, fromRequest(snippetRequest, url.toString()))

        return ResponseEntity(
            toResponse(snippet),
            HttpStatus.CREATED
        )
    }

    @GetMapping("/snippets/{snippetId}")
    fun getSnippet(@PathVariable snippetId: String, request: HttpServletRequest): ResponseEntity<SnippetResponse> {
        val url = URL(request.requestURL.toString())
        val path = url.path
        // use uri as ID
        val snippet = snippetService.readSnippet(path)
        return ResponseEntity(toResponse(snippet), HttpStatus.OK)
    }

    @PostMapping("/snippets/{snippetId}/like")
    fun likeSnippet(@PathVariable snippetId: String, request: HttpServletRequest): ResponseEntity<SnippetResponse> {
        val snippet = snippetService.like("/snippets/$snippetId")
        return ResponseEntity(toResponse(snippet), HttpStatus.OK)
    }

    /**
     * Convert from SnippetRequest to Snippet.
     *
     * @param snippetRequest The snippet request
     * @param url The url that is used for accessing the snippet.
     * @return the converted snippet object.
     */
    private fun fromRequest(snippetRequest: SnippetRequest, url:String) : Snippet {
        var exp = System.currentTimeMillis() / 1000L
        return Snippet(url, snippetRequest.name, exp, snippetRequest.snippet)
    }

    /**
     * Convert from a snippet to a SnippetResponse
     *
     * @param snippet Snippet object
     * @return the converted SnippetResponse object.
     */
    private fun toResponse(snippet: Snippet): SnippetResponse {
        val exp = LocalDateTime.ofInstant(
            Instant.ofEpochSecond(snippet.expires_at),
            TimeZone.getDefault().toZoneId()
        )

        return SnippetResponse(snippet.url, snippet.name, exp.toString(), snippet.snippet, snippet.likes)
    }
}
