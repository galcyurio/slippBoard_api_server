package net.slipp.controller

import net.slipp.repository.domain.User
import net.slipp.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

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
        userService.signOn(user)
        return ResponseEntity(user, HttpStatus.CREATED)
    }
}