package net.slipp.controller

import net.slipp.repository.domain.User
import net.slipp.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.RequestContextHolder

/**
 * @author galcyurio
 */
@RestController
class UserController(
    private val userService: UserService
) {

    @GetMapping("/public")
    fun public(): String {
        return "public resource"
    }

    @GetMapping("/protected")
    fun protected(): String {
        return "protected resource"
    }

    @PostMapping("/signOn")
    fun signOn(@RequestBody user: User): ResponseEntity<User> {
        val newUser = userService.signOn(user)
        return ResponseEntity(newUser, HttpStatus.CREATED)
    }

    @GetMapping("/signIn")
    fun signIn(): ResponseEntity<Any> {
        val sessionId = RequestContextHolder.currentRequestAttributes().sessionId
        return ResponseEntity.ok(sessionId)
    }
}