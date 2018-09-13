package net.slipp.repository

import net.slipp.repository.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author galcyurio
 */
@RunWith(SpringRunner::class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `User 생성 조회 삭제`() {
        val user = User("user", "raw-password", setOf(SimpleGrantedAuthority("ROLE_USER")))

        val savedUser = userRepository.save(user)
        assertThat(user).isEqualTo(savedUser)

        val foundUser = userRepository.findByUsername("user")
        assertThat(user).isEqualTo(foundUser.get())

        userRepository.delete(user)
    }
}