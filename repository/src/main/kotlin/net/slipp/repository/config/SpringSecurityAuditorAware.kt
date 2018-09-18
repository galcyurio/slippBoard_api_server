package net.slipp.repository.config

import net.slipp.repository.domain.User
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
class SpringSecurityAuditorAware : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        return Optional.ofNullable(user.username)
    }
}