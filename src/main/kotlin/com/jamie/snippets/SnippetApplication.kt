package com.jamie.snippets

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.util.*

/**
 * The Spring application. No beans are defined here because it is not necessary for the Snippet.
 *
 */
@SpringBootApplication
class SnippetApplication {
}

fun main(args: Array<String>) {
    val props = Properties()
    props["spring.application.name"] = "snippet_web"
    props["server.port"] = 8080

    val app = SpringApplication(SnippetApplication::class.java)
    app.setDefaultProperties(props)
    app.run(*args)
}
