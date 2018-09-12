package net.slipp.service

import net.slipp.misc.AlreadyExistsEmailException
import net.slipp.repository.UserRepository
import net.slipp.repository.domain.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

/**
 * @author galcyurio
 */
@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("해당되는 username이 존재하지 않습니다.") }
    }

    fun signOn(user: User): User {
        if (!userRepository.findByUsername(user.username).isPresent) {
            val newUser = user.copy(password = passwordEncoder.encode(user.password))
            return userRepository.save(newUser)
        } else {
            throw AlreadyExistsEmailException("존재하는 email 입니다.")
        }
    }
}