package com.jamie.quickweb.api

import com.jamie.quickweb.model.Snippet
import com.jamie.quickweb.service.SnippetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URL
import java.time.Instant
import java.time.LocalDateTime
import java.util.*
import javax.servlet.http.HttpServletRequest


@RestController
class QuickWebApi(
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


    private fun fromRequest(sr: SnippetRequest, url:String) : Snippet {
        var exp = System.currentTimeMillis() / 1000L
        return Snippet(url, sr.name, exp, sr.snippet)
    }

    private fun toResponse(s: Snippet): SnippetResponse {
        val exp = LocalDateTime.ofInstant(
            Instant.ofEpochSecond(s.expires_at),
            TimeZone.getDefault().toZoneId()
        )

        return SnippetResponse(s.url, s.name, exp.toString(), s.snippet, s.likes)
    }
}
