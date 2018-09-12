package net.slipp.service

import net.slipp.repository.UserRepository
import net.slipp.repository.domain.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

/**
 * @author galcyurio
 */
@Service
class UserService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.getByUsername(username)
    }

    fun signOn(user: User) {
        if (!userRepository.findByUsername(user.username).isPresent) {
            userRepository.save(user)
        } else {
            throw RuntimeException("존재하는 email 입니다.")
        }
    }
}