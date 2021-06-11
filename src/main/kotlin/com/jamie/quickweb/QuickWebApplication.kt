package com.jamie.quickweb

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.util.*

@SpringBootApplication
class PolarBearApplication {
}

fun main(args: Array<String>) {
    val props = Properties()
    props["spring.application.name"] = "quickweb"
    props["server.port"] = 8080

    val app = SpringApplication(PolarBearApplication::class.java)
    app.setDefaultProperties(props)
    app.run(*args)
}
