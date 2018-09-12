package net.slipp.misc

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * @author galcyurio
 */
@ControllerAdvice
class ErrorControllerAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException): ResponseEntity<Any> {
        return ResponseEntity.badRequest()
            .body(mapOf("message" to e.message))
    }
}