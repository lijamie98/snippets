package com.jamie.quickweb.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidArgumentException(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.CONFLICT)
class AlreadyExistsException(message: String) : RuntimeException(message)

